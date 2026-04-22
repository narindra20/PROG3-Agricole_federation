package hei.school.agricole.service;

import hei.school.agricole.dto.AssignIdentityRequest;
import hei.school.agricole.dto.CreateCollectivity;
import hei.school.agricole.entity.Collectivity;
import hei.school.agricole.repository.CollectivityRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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
        c.setCreationDate(LocalDate.now());
        c.setCityId(mapLocation(dto.getLocation()));
        c.setDomainId(1);
        c.setFederationId(dto.isFederationApproval() ? 1 : null);
        c.setSectorId(null);
        c.setAuthorized(dto.isFederationApproval());

        try {
            return repository.save(c);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create collectivity", e);
        }
    }


    public Collectivity assignIdentity(int id, AssignIdentityRequest dto) {

        try {
            Collectivity c = repository.findById(id);

            if (c == null) {
                throw new RuntimeException("Collectivity not found");
            }

            if (c.getNumber() != null || c.getName() != null) {
                throw new RuntimeException("Identity already assigned");
            }

            if (repository.existsByNumber(dto.getNumber())) {
                throw new RuntimeException("Number already exists");
            }

            if (repository.existsByName(dto.getName())) {
                throw new RuntimeException("Name already exists");
            }

            c.setNumber(dto.getNumber());
            c.setName(dto.getName());

            return repository.updateIdentity(c);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to assign identity", e);
        }
    }

    private void validate(CreateCollectivity dto) {
        if (dto.getLocation() == null || dto.getLocation().isBlank()) {
            throw new RuntimeException("location required");
        }
    }

    private int mapLocation(String location) {
        return 1;
    }

    public List<Collectivity> findAll() {
        try {
            return repository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch collectivities", e);
        }
    }
}