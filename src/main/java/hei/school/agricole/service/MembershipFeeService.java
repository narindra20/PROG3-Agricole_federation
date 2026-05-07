package hei.school.agricole.service;

import hei.school.agricole.dto.CreateMembershipFee;
import hei.school.agricole.entity.MembershipFee;
import hei.school.agricole.repository.CollectivityRepository;
import hei.school.agricole.repository.MembershipFeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MembershipFeeService {
    private final MembershipFeeRepository feeRepository;
    private final CollectivityRepository collectivityRepository;

    public MembershipFeeService(MembershipFeeRepository feeRepository, CollectivityRepository collectivityRepository) {
        this.feeRepository = feeRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<MembershipFee> getByCollectivity(String collectivityId) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new RuntimeException("Collectivity not found");
        }
        return feeRepository.findByCollectivityId(collectivityId);
    }

    public List<MembershipFee> create(String collectivityId, List<CreateMembershipFee> fees) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new RuntimeException("Collectivity not found");
        }
        for (CreateMembershipFee fee : fees) {
            if (fee.getAmount() == null || fee.getAmount() <= 0) {
                throw new RuntimeException("Amount must be > 0");
            }
            if (fee.getFrequency() == null) {
                throw new RuntimeException("Invalid frequency");
            }
        }
        return feeRepository.saveAll(collectivityId, fees);
    }
}