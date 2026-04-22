package hei.school.agricole.service;

import hei.school.agricole.dto.CreateCollectivity;
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

    public Collectivity create(CreateCollectivity dto) {

        validate(dto);

        Collectivity c = new Collectivity();

        c.setNumber(generateNumber());
        c.setName("COLL-" + dto.getLocation());
        c.setCreationDate(LocalDate.now());
        c.setCityId(mapLocation(dto.getLocation()));
        c.setDomainId(1);
        c.setFederationId(dto.isFederationApproval() ? 1 : null);
        c.setSectorId(null);
        c.setAuthorized(dto.isFederationApproval());
        c.setLocation(dto.getLocation());

        return repository.save(c);
    }

    private void validate(CreateCollectivity dto) {
        if (dto == null) {
            throw new RuntimeException("DTO cannot be null");
        }

        if (dto.getLocation() == null || dto.getLocation().isBlank()) {
            throw new RuntimeException("location required");
        }
    }

    private int generateNumber() {
        return (int) (Math.random() * 100000);
    }


    private int mapLocation(String location) {
        return switch (location.toLowerCase()) {
            case "antananarivo" -> 1;
            case "toamasina" -> 2;
            case "fianarantsoa" -> 3;
            default -> 1;
        };
    }

    public List<Collectivity> findAll() {
        return repository.findAll();
    }
}