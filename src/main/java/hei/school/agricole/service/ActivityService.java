package hei.school.agricole.service;

import hei.school.agricole.dto.ActivityMemberAttendance;
import hei.school.agricole.dto.CreateActivity;
import hei.school.agricole.dto.CreateActivityMemberAttendance;
import hei.school.agricole.dto.MemberDescription;
import hei.school.agricole.entity.Activity;
import hei.school.agricole.entity.Attendance;
import hei.school.agricole.entity.Member;
import hei.school.agricole.repository.ActivityRepository;
import hei.school.agricole.repository.AttendanceRepository;
import hei.school.agricole.repository.CollectivityRepository;
import hei.school.agricole.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final AttendanceRepository attendanceRepository;
    private final CollectivityRepository collectivityRepository;
    private final MemberRepository memberRepository;

    public ActivityService(ActivityRepository activityRepository,
                           AttendanceRepository attendanceRepository,
                           CollectivityRepository collectivityRepository,
                           MemberRepository memberRepository) {
        this.activityRepository = activityRepository;
        this.attendanceRepository = attendanceRepository;
        this.collectivityRepository = collectivityRepository;
        this.memberRepository = memberRepository;
    }

    public List<Activity> createActivities(String collectivityId, List<CreateActivity> activities) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collectivity not found");
        }
        List<Activity> result = new ArrayList<>();
        for (CreateActivity ca : activities) {
            Activity a = new Activity();
            a.setLabel(ca.getLabel());
            a.setActivityType(ca.getActivityType());
            List<String> occupations = ca.getMemberOccupationConcerned();
            if (occupations == null) {
                occupations = new ArrayList<>();
            }
            a.setMemberOccupationConcerned(occupations);
            a.setExecutiveDate(ca.getExecutiveDate());
            result.add(activityRepository.save(collectivityId, a));
        }
        return result;
    }

    public List<Activity> getActivities(String collectivityId) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collectivity not found");
        }
        return activityRepository.findByCollectivityId(collectivityId);
    }

    public List<ActivityMemberAttendance> recordAttendance(String collectivityId, String activityId, List<CreateActivityMemberAttendance> records) {
        Activity activity = activityRepository.findById(activityId);
        if (activity == null || !activity.getCollectivityId().equals(collectivityId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found or does not belong to collectivity");
        }
        List<ActivityMemberAttendance> response = new ArrayList<>();
        for (CreateActivityMemberAttendance rec : records) {
            Member member = memberRepository.findById(rec.getMemberIdentifier());
            if (member == null || !member.getCollectivityId().equals(collectivityId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member not found or not in this collectivity");
            }
            if (attendanceRepository.existsByActivityAndMember(activityId, rec.getMemberIdentifier())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attendance already recorded for member " + rec.getMemberIdentifier());
            }
            Attendance att = new Attendance();
            att.setMemberId(rec.getMemberIdentifier());
            att.setAttendanceStatus(rec.getAttendanceStatus());
            List<Attendance> list = new ArrayList<>();
            list.add(att);
            attendanceRepository.saveAll(activityId, list);

            ActivityMemberAttendance ama = new ActivityMemberAttendance();
            ama.setId(att.getId());
            MemberDescription md = new MemberDescription();
            md.setId(member.getId());
            md.setFirstName(member.getFirstName());
            md.setLastName(member.getLastName());
            md.setEmail(member.getEmail());
            md.setOccupation(member.getOccupation() != null ? member.getOccupation().name() : null);
            ama.setMemberDescription(md);
            ama.setAttendanceStatus(rec.getAttendanceStatus());
            response.add(ama);
        }
        return response;
    }

    public List<ActivityMemberAttendance> getAttendance(String collectivityId, String activityId) {
        Activity activity = activityRepository.findById(activityId);
        if (activity == null || !activity.getCollectivityId().equals(collectivityId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found or does not belong to collectivity");
        }
        List<Attendance> attendances = attendanceRepository.findByActivityId(activityId);
        List<ActivityMemberAttendance> result = new ArrayList<>();
        for (Attendance a : attendances) {
            Member member = memberRepository.findById(a.getMemberId());
            if (member == null) continue;
            ActivityMemberAttendance ama = new ActivityMemberAttendance();
            ama.setId(a.getId());
            MemberDescription md = new MemberDescription();
            md.setId(member.getId());
            md.setFirstName(member.getFirstName());
            md.setLastName(member.getLastName());
            md.setEmail(member.getEmail());
            md.setOccupation(member.getOccupation() != null ? member.getOccupation().name() : null);
            ama.setMemberDescription(md);
            ama.setAttendanceStatus(a.getAttendanceStatus());
            result.add(ama);
        }
        return result;
    }

    public Double computeAttendanceRate(String memberId, String collectivityId, LocalDate from, LocalDate to) {
        List<Activity> activities = activityRepository.findByCollectivityId(collectivityId);
        long totalObligatory = 0;
        long totalPresent = 0;
        for (Activity a : activities) {
            if (a.getExecutiveDate().isBefore(from) || a.getExecutiveDate().isAfter(to)) continue;
            List<String> concerned = a.getMemberOccupationConcerned();
            Member member = memberRepository.findById(memberId);
            if (member == null) continue;
            String role = member.getOccupation().name();
            boolean isObligatory = concerned == null || concerned.isEmpty() || concerned.contains("ALL") || concerned.contains(role);
            if (!isObligatory) continue;
            totalObligatory++;
            Attendance att = attendanceRepository.findByActivityIdAndMemberId(a.getId(), memberId);
            if (att != null && "PRESENT".equals(att.getAttendanceStatus())) totalPresent++;
        }
        if (totalObligatory == 0) return 0.0;
        return (totalPresent * 100.0) / totalObligatory;
    }
}