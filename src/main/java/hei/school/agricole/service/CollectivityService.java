package hei.school.agricole.service;

import hei.school.agricole.dto.AssignIdentityRequest;
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

        c.setLocation(dto.getLocation());
        c.setCreationDate(LocalDate.now());
        c.setCityId(mapLocation(dto.getLocation()));
        c.setDomainId(1);

        c.setFederationId(dto.isFederationApproval() ? 1 : null);
        c.setSectorId(null);
        c.setAuthorized(dto.isFederationApproval());

        c.setNumber(null);
        c.setName(null);

        return repository.save(c);
    }

    public Collectivity assignIdentity(String id, AssignIdentityRequest request) {

        if (request == null) {
            throw new RuntimeException("Identity request cannot be null");
        }

        if (request.getNumber() == null || request.getNumber().isBlank()) {
            throw new RuntimeException("number required");
        }

        if (request.getName() == null || request.getName().isBlank()) {
            throw new RuntimeException("name required");
        }

        if (repository.existsByNumberOrName(request.getNumber(), request.getName())) {
            throw new RuntimeException("Number or name already exists");
        }

        Collectivity updated = repository.updateIdentity(
                id,
                request.getNumber(),
                request.getName()
        );

        if (updated == null) {
            throw new RuntimeException("Collectivity not found");
        }

        return updated;
    }

    public List<Collectivity> findAll() {
        return repository.findAll();
    }

    private void validate(CreateCollectivity dto) {

        if (dto == null) {
            throw new RuntimeException("DTO cannot be null");
        }

        if (dto.getLocation() == null || dto.getLocation().isBlank()) {
            throw new RuntimeException("location required");
        }

        if (!dto.isFederationApproval()) {
            throw new RuntimeException("Federation approval required");
        }
    }

    private int mapLocation(String location) {
        return switch (location.toLowerCase()) {
            case "antananarivo" -> 1;
            case "toamasina" -> 2;
            case "fianarantsoa" -> 3;
            default -> 1;
        };
    }
}