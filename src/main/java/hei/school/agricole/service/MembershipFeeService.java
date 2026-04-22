package hei.school.agricole.service;

import hei.school.agricole.dto.CreateMembershipFee;
import hei.school.agricole.entity.MembershipFee;
import hei.school.agricole.repository.CollectivityRepository;
import hei.school.agricole.repository.MembershipFeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipFeeService {

    private final MembershipFeeRepository repository;
    private final CollectivityRepository collectivityRepository;

    public MembershipFeeService(MembershipFeeRepository repository,
                                CollectivityRepository collectivityRepository) {
        this.repository = repository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<MembershipFee> getByCollectivity(String id) {

        if (collectivityRepository.findById(Integer.parseInt(id)) == null) {
            throw new RuntimeException("Collectivity not found");
        }

        return repository.findByCollectivityId(id);
    }

    public List<MembershipFee> create(String id, List<CreateMembershipFee> fees) {

        if (collectivityRepository.findById(Integer.parseInt(id)) == null) {
            throw new RuntimeException("Collectivity not found");
        }

        for (CreateMembershipFee f : fees) {

            if (f.getAmount() == null || f.getAmount() <= 0) {
                throw new RuntimeException("Amount must be > 0");
            }

            if (f.getFrequency() == null) {
                throw new RuntimeException("Invalid frequency");
            }
        }

        return repository.saveAll(id, fees);
    }

    private boolean isValidFrequency(String f) {
        return f.equals("WEEKLY")
                || f.equals("MONTHLY")
                || f.equals("ANNUALLY")
                || f.equals("PUNCTUALLY");
    }
}