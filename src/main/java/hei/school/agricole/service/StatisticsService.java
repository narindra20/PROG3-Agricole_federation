package hei.school.agricole.service;

import hei.school.agricole.dto.*;
import hei.school.agricole.entity.*;
import hei.school.agricole.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService {

    private final CollectivityRepository collectivityRepository;
    private final MemberRepository memberRepository;
    private final MembershipFeeRepository feeRepository;
    private final MemberPaymentRepository paymentRepository;
    private final ActivityRepository activityRepository;
    private final AttendanceRepository attendanceRepository;

    public StatisticsService(CollectivityRepository collectivityRepository,
                             MemberRepository memberRepository,
                             MembershipFeeRepository feeRepository,
                             MemberPaymentRepository paymentRepository,
                             ActivityRepository activityRepository,
                             AttendanceRepository attendanceRepository) {
        this.collectivityRepository = collectivityRepository;
        this.memberRepository = memberRepository;
        this.feeRepository = feeRepository;
        this.paymentRepository = paymentRepository;
        this.activityRepository = activityRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public List<CollectivityLocalStatistics> getMemberStatistics(String collectivityId, LocalDate from, LocalDate to) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new RuntimeException("Collectivity not found");
        }
        List<Member> members = memberRepository.findActiveMembersByCollectivityId(collectivityId);
        List<MembershipFee> activeFees = feeRepository.findActiveFeesByCollectivityId(collectivityId);
        List<CollectivityLocalStatistics> result = new ArrayList<>();
        for (Member member : members) {
            CollectivityLocalStatistics stat = new CollectivityLocalStatistics();
            MemberDescription md = new MemberDescription();
            md.setId(member.getId());
            md.setFirstName(member.getFirstName());
            md.setLastName(member.getLastName());
            md.setEmail(member.getEmail());
            md.setOccupation(member.getOccupation() != null ? member.getOccupation().name() : null);
            stat.setMemberDescription(md);

            double totalPayments = 0.0;
            double totalDue = 0.0;
            for (MembershipFee fee : activeFees) {
                double paid = paymentRepository.getTotalPaymentsByMemberAndFeeBetweenDates(member.getId(), fee.getId(), from, to);
                totalPayments += paid;
                if (!fee.getEligibleFrom().isAfter(to)) {
                    totalDue += fee.getAmount();
                }
            }
            stat.setEarnedAmount(totalPayments);
            double outstanding = totalDue - totalPayments;
            stat.setUnpaidAmount(outstanding > 0 ? outstanding : 0.0);
            stat.setAssiduityPercentage(computeMemberAttendanceRate(member.getId(), collectivityId, from, to));
            result.add(stat);
        }
        return result;
    }

    public List<CollectivityOverallStatistics> getAllCollectivitiesStatistics(LocalDate from, LocalDate to) {
        List<String> collectivityIds = collectivityRepository.findAllIds();
        List<CollectivityOverallStatistics> result = new ArrayList<>();
        for (String id : collectivityIds) {
            List<Member> members = memberRepository.findActiveMembersByCollectivityId(id);
            List<MembershipFee> activeFees = feeRepository.findActiveFeesByCollectivityId(id);
            int newMembers = memberRepository.countNewMembersByCollectivityIdBetweenDates(id, from, to);
            int membersUpToDate = 0;
            for (Member member : members) {
                boolean upToDate = true;
                for (MembershipFee fee : activeFees) {
                    if (!fee.getEligibleFrom().isAfter(to)) {
                        double paid = paymentRepository.getTotalPaymentsByMemberAndFeeBetweenDates(member.getId(), fee.getId(), from, to);
                        if (paid < fee.getAmount()) {
                            upToDate = false;
                            break;
                        }
                    }
                }
                if (upToDate) membersUpToDate++;
            }
            double percentage = members.isEmpty() ? 0.0 : (membersUpToDate * 100.0 / members.size());
            CollectivityOverallStatistics stat = new CollectivityOverallStatistics();
            CollectivityInformation ci = new CollectivityInformation();
            Collectivity col = collectivityRepository.findById(id);
            if (col != null) {
                ci.setName(col.getName());
                ci.setNumber(col.getNumber());
            }
            stat.setCollectivityInformation(ci);
            stat.setNewMembersNumber(newMembers);
            stat.setOverallMemberCurrentDuePercentage(percentage);
            stat.setOverallMemberAssiduityPercentage(computeCollectivityAttendanceRate(id, from, to));
            result.add(stat);
        }
        return result;
    }

    private double computeMemberAttendanceRate(String memberId, String collectivityId, LocalDate from, LocalDate to) {
        List<Activity> activities = activityRepository.findByCollectivityId(collectivityId);
        int totalMandatory = 0;
        int attended = 0;
        for (Activity a : activities) {
            if (a.getExecutiveDate().isBefore(from) || a.getExecutiveDate().isAfter(to)) continue;
            if ("MEETING".equals(a.getActivityType()) || "TRAINING".equals(a.getActivityType())) {
                totalMandatory++;
                List<Attendance> attList = attendanceRepository.findByActivityId(a.getId());
                boolean present = attList.stream()
                        .filter(att -> att.getMemberId().equals(memberId))
                        .findFirst()
                        .map(att -> "ATTENDED".equals(att.getAttendanceStatus()))
                        .orElse(false);
                if (present) attended++;
            }
        }
        return totalMandatory == 0 ? 100.0 : (attended * 100.0 / totalMandatory);
    }

    private double computeCollectivityAttendanceRate(String collectivityId, LocalDate from, LocalDate to) {
        List<Member> members = memberRepository.findActiveMembersByCollectivityId(collectivityId);
        if (members.isEmpty()) return 0.0;
        double totalRate = 0.0;
        for (Member m : members) {
            totalRate += computeMemberAttendanceRate(m.getId(), collectivityId, from, to);
        }
        return totalRate / members.size();
    }
}