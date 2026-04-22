package hei.school.agricole.service;

import hei.school.agricole.dto.CreateCollectivity;
import hei.school.agricole.dto.CollectivityInformation;
import hei.school.agricole.entity.Collectivity;
import hei.school.agricole.repository.CollectivityRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class CollectivityService {

    private final CollectivityRepository repository;

    public CollectivityService(CollectivityRepository repository) {
        this.repository = repository;
    }

    public List<Collectivity> findAll() {
        return repository.findAll();
    }

    public Collectivity create(CreateCollectivity request) {
        Collectivity collectivity = new Collectivity();
        collectivity.setName(request.getName());
        collectivity.setNumber(request.getNumber());
        collectivity.setLocation(request.getLocation());
        collectivity.setCreationDate(LocalDate.now());
        return repository.save(collectivity);
    }

    public Collectivity updateInformations(String id, CollectivityInformation request) {
        Collectivity existing = repository.findById(id);
        if (existing == null) {
            throw new RuntimeException("Collectivity not found");
        }

        existing.setName(request.getName());
        existing.setNumber(request.getNumber());
        return repository.save(existing);
    }
}