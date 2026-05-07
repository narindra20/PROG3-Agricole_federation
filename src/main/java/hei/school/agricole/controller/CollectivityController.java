package hei.school.agricole.controller;

import hei.school.agricole.dto.*;
import hei.school.agricole.entity.Collectivity;
import hei.school.agricole.service.CollectivityService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
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
        return requests.stream().map(service::create).toList();
    }

    @PutMapping("/{id}/informations")
    public Collectivity updateInformations(
            @PathVariable String id,
            @RequestBody CollectivityInformation request) {
        return service.updateInformations(id, request);
    }

    @GetMapping("/{id}")
    public CollectivityWithMembers getById(@PathVariable String id) {
        return service.getCollectivityWithMembers(id);
    }

    @GetMapping("/{id}/financialAccounts")
    public List<FinancialAccountResponse> getFinancialAccounts(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate at) {
        return service.getFinancialAccounts(id, at);
    }

    @GetMapping("/{id}/statistics")
    public List<MemberStatistics> getCollectivityStatistics(
            @PathVariable String id,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return service.getMemberStatistics(id, from, to);
    }

    @GetMapping("/statistics")
    public List<FederationStatisticsDto> getFederationStatistics(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return service.getFederationStatistics(from, to);
    }
}