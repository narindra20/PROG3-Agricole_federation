package hei.school.agricole.service;

import hei.school.agricole.entity.CollectivityTransaction;
import hei.school.agricole.repository.CollectivityRepository;
import hei.school.agricole.repository.CollectivityTransactionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class CollectivityTransactionService {
    private final CollectivityTransactionRepository transactionRepository;
    private final CollectivityRepository collectivityRepository;

    public CollectivityTransactionService(CollectivityTransactionRepository transactionRepository,
                                          CollectivityRepository collectivityRepository) {
        this.transactionRepository = transactionRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<CollectivityTransaction> getTransactions(String collectivityId, LocalDate from, LocalDate to) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new RuntimeException("Collectivity not found");
        }
        if (from == null || to == null) {
            throw new RuntimeException("Query parameters 'from' and 'to' are mandatory");
        }
        return transactionRepository.findByCollectivityIdAndDateBetween(collectivityId, from, to);
    }
}