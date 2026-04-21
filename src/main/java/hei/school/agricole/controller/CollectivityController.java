package hei.school.agricole.controller;

import hei.school.agricole.dto.CreateCollectivity;
import hei.school.agricole.entity.Collectivity;
import hei.school.agricole.service.CollectivityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class CollectivityController {

    private final CollectivityService service;

    public CollectivityController(CollectivityService service) {
        this.service = service;
    }

    @GetMapping
    public List<Collectivity> getAll() {
        return service.findAll();
    }

    @PostMapping
    public List<Collectivity> create(@RequestBody List<CreateCollectivity> requests) {
        return requests.stream()
                .map(service::create)
                .toList();
    }
}