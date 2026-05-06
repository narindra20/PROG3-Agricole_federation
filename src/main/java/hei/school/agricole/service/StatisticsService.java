package hei.school.agricole.service;

import hei.school.agricole.dto.CollectivityLocalStatistics;
import hei.school.agricole.dto.CollectivityOverallStatistics;
import hei.school.agricole.repository.CollectivityRepository;
import hei.school.agricole.repository.MemberRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class StatisticsService {

    private final MemberRepository memberRepository;
    private final CollectivityRepository collectivityRepository;

    public StatisticsService(MemberRepository memberRepository, CollectivityRepository collectivityRepository) {
        this.memberRepository = memberRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<CollectivityLocalStatistics> getMemberStatistics(String collectivityId, LocalDate from, LocalDate to) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new RuntimeException("Collectivity not found");
        }
        return memberRepository.getLocalStatistics(collectivityId, from, to);
    }

    public List<CollectivityOverallStatistics> getAllCollectivitiesStatistics(LocalDate from, LocalDate to) {
        return collectivityRepository.getOverallStatistics(from, to);
    }
}