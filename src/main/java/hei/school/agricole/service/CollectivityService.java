package hei.school.agricole.service;

import hei.school.agricole.dto.CollectivityInformation;
import hei.school.agricole.dto.CollectivityWithMembers;
import hei.school.agricole.dto.CreateCollectivity;
import hei.school.agricole.dto.FinancialAccountResponse;
import hei.school.agricole.dto.FederationStatisticsDto;
import hei.school.agricole.dto.MemberStatistics;
import hei.school.agricole.entity.*;
import hei.school.agricole.repository.CollectivityRepository;
import hei.school.agricole.repository.FinancialAccountRepository;
import hei.school.agricole.repository.MemberRepository;
import hei.school.agricole.repository.MemberPaymentRepository;
import hei.school.agricole.repository.MembershipFeeRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CollectivityService {

    private final CollectivityRepository repository;
    private final MemberRepository memberRepository;
    private final FinancialAccountRepository accountRepository;
    private final MemberPaymentRepository paymentRepository;
    private final MembershipFeeRepository membershipFeeRepository;
    private final ActivityService activityService;

    public CollectivityService(CollectivityRepository repository,
                               MemberRepository memberRepository,
                               FinancialAccountRepository accountRepository,
                               MemberPaymentRepository paymentRepository,
                               MembershipFeeRepository membershipFeeRepository,
                               ActivityService activityService) {
        this.repository = repository;
        this.memberRepository = memberRepository;
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
        this.membershipFeeRepository = membershipFeeRepository;
        this.activityService = activityService;
    }

    public List<Collectivity> findAll() {
        return repository.findAll();
    }

    public Collectivity findById(String id) {
        return repository.findById(id);
    }

    public Collectivity create(CreateCollectivity request) {
        Collectivity collectivity = new Collectivity();
        collectivity.setName(null);
        collectivity.setNumber(null);
        collectivity.setLocation(request.getLocation());
        collectivity.setCreationDate(LocalDate.now());
        return repository.save(collectivity);
    }

    public Collectivity updateInformations(String id, CollectivityInformation request) {
        Collectivity existing = repository.findById(id);
        if (existing == null) {
            throw new RuntimeException("Collectivity not found");
        }
        if (existing.getName() != null && existing.getNumber() != null) {
            throw new RuntimeException("Name and number cannot be changed once attributed");
        }
        if (repository.existsByName(request.getName())) {
            throw new RuntimeException("Name already used by another collectivity");
        }
        if (repository.existsByNumber(request.getNumber())) {
            throw new RuntimeException("Number already used by another collectivity");
        }
        repository.updateInformation(id, request.getName(), request.getNumber());
        existing.setName(request.getName());
        existing.setNumber(request.getNumber());
        return existing;
    }

    public CollectivityWithMembers getCollectivityWithMembers(String id) {
        Collectivity collectivity = repository.findById(id);
        if (collectivity == null) {
            throw new RuntimeException("Collectivity not found");
        }
        List<Member> members = memberRepository.findByCollectivityId(id);
        CollectivityWithMembers response = new CollectivityWithMembers();
        response.setId(collectivity.getId());
        response.setName(collectivity.getName());
        response.setNumber(collectivity.getNumber());
        response.setLocation(collectivity.getLocation());
        response.setCreationDate(collectivity.getCreationDate());
        response.setMembers(members);
        return response;
    }

    public List<FinancialAccountResponse> getFinancialAccounts(String collectivityId, LocalDate atDate) {
        if (!repository.existsById(collectivityId)) {
            throw new RuntimeException("Collectivity not found");
        }
        List<FinancialAccount> accounts = accountRepository.findByCollectivityId(collectivityId);
        List<FinancialAccountResponse> responses = new ArrayList<>();
        for (FinancialAccount acc : accounts) {
            FinancialAccountResponse resp = new FinancialAccountResponse();
            resp.setId(acc.getId());
            if (acc instanceof CashAccount) {
                resp.setType("CASH");
            } else if (acc instanceof MobileBankingAccount) {
                resp.setType("MOBILE");
            } else if (acc instanceof BankAccount) {
                resp.setType("BANK");
            }
            resp.setBalance(accountRepository.getBalanceAtDate(acc.getId(), atDate));
            resp.setHolderName(acc.getHolderName());
            if (acc instanceof BankAccount) {
                BankAccount ba = (BankAccount) acc;
                resp.setBankName(ba.getBankName().name());
                resp.setAccountNumber(ba.getBankAccountNumber());
            } else if (acc instanceof MobileBankingAccount) {
                MobileBankingAccount ma = (MobileBankingAccount) acc;
                resp.setMobileService(ma.getMobileBankingService().name());
                resp.setAccountNumber(ma.getMobileNumber());
            } else {
                resp.setAccountNumber(null);
            }
            responses.add(resp);
        }
        return responses;
    }

    public List<MemberStatistics> getMemberStatistics(String collectivityId, LocalDate from, LocalDate to) {
        List<Member> members = memberRepository.findByCollectivityId(collectivityId);
        List<MembershipFee> activeFees = membershipFeeRepository.findByCollectivityIdAndStatus(collectivityId, "ACTIVE");
        List<MemberStatistics> result = new ArrayList<>();
        for (Member member : members) {
            Double totalPaid = paymentRepository.getTotalPaymentsByMemberBetweenDates(member.getId(), from, to);
            Double totalDue = computeTotalDue(member, activeFees, from, to);
            Double outstanding = Math.max(totalDue - totalPaid, 0.0);
            Double attendanceRate = activityService.computeAttendanceRate(member.getId(), collectivityId, from, to);
            MemberStatistics stats = new MemberStatistics();
            stats.setMemberId(member.getId());
            stats.setTotalPayments(totalPaid);
            stats.setPotentialOutstanding(outstanding);
            stats.setAttendanceRate(attendanceRate);
            result.add(stats);
        }
        return result;
    }

    public List<FederationStatisticsDto> getFederationStatistics(LocalDate from, LocalDate to) {
        List<Collectivity> collectivities = repository.findAll();
        List<FederationStatisticsDto> result = new ArrayList<>();
        for (Collectivity col : collectivities) {
            List<Member> members = memberRepository.findByCollectivityId(col.getId());
            List<MembershipFee> activeFees = membershipFeeRepository.findByCollectivityIdAndStatus(col.getId(), "ACTIVE");
            long totalMembers = members.size();
            long upToDate = members.stream().filter(m -> {
                Double paid = paymentRepository.getTotalPaymentsByMemberBetweenDates(m.getId(), from, to);
                Double due = computeTotalDue(m, activeFees, from, to);
                return paid >= due;
            }).count();
            long newCount = members.stream()
                    .filter(m -> m.getMembershipDate() != null && !m.getMembershipDate().isBefore(from) && !m.getMembershipDate().isAfter(to))
                    .count();
            Double percentage = totalMembers == 0 ? 0.0 : (upToDate * 100.0 / totalMembers);
            double avgAttendance = members.stream()
                    .mapToDouble(m -> activityService.computeAttendanceRate(m.getId(), col.getId(), from, to))
                    .average()
                    .orElse(0.0);
            FederationStatisticsDto dto = new FederationStatisticsDto();
            dto.setCollectivityId(col.getId());
            dto.setPercentageUpToDateMembers(percentage);
            dto.setNewMembersCount((int) newCount);
            dto.setGlobalAttendanceRate(avgAttendance);
            result.add(dto);
        }
        return result;
    }

    private Double computeTotalDue(Member member, List<MembershipFee> activeFees, LocalDate from, LocalDate to) {
        double sum = 0.0;
        for (MembershipFee fee : activeFees) {
            if (fee.getEligibleFrom() != null && !fee.getEligibleFrom().isAfter(member.getMembershipDate()) && fee.getEligibleFrom().isBefore(to)) {
                sum += fee.getAmount();
            }
        }
        return sum;
    }
}