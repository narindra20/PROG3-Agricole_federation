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

        if (!Boolean.TRUE.equals(request.getFederationApproval())) {
            throw new RuntimeException("Federation approval required");
        }

        Collectivity c = new Collectivity();
        c.setLocation(request.getLocation());
        c.setCreationDate(LocalDate.now());
        c.setAuthorized(true);

        c.setCityId(request.getCityId());
        c.setDomainId(request.getDomainId());
        c.setFederationId(request.getFederationId());

        return repository.save(c);
    }

    public Collectivity updateInformations(String id, CollectivityInformation request) {

        int collectivityId = Integer.parseInt(id);

        Collectivity c = repository.findById(collectivityId);

        if (c == null) {
            throw new RuntimeException("Collectivity not found");
        }

        c.setName(request.getName());
        c.setNumber(request.getNumber());

        return repository.updateInformations(c);
    }
}