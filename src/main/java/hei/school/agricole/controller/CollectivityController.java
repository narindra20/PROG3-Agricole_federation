package hei.school.agricole.controller;

import hei.school.agricole.dto.CreateCollectivity;
import hei.school.agricole.dto.CollectivityInformation;
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
    public Collectivity create(@RequestBody CreateCollectivity request) {
        return service.create(request);
    }

    @PutMapping("/{id}/informations")
    public Collectivity updateInformations(
            @PathVariable String id,
            @RequestBody CollectivityInformation request
    ) {
        return service.updateInformations(id, request);
    }

}