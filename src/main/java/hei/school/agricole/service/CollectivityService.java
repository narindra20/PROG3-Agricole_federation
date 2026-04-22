package hei.school.agricole.service;

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

        c.setLocation(dto.getLocation());
        c.setCreationDate(LocalDate.now());
        c.setAuthorized(dto.isFederationApproval());

        try {
            Collectivity saved = repository.save(c);

            return saved;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create collectivity", e);
        }
    }

    private void validate(CreateCollectivity dto) {
        if (dto.getLocation() == null || dto.getLocation().isBlank()) {
            throw new RuntimeException("location required");
        }
    }

    public List<Collectivity> findAll() {
        try {
            return repository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch collectivities", e);
        }
    }
}