package hei.school.agricole.controller;

import hei.school.agricole.dto.CollectivityLocalStatistics;
import hei.school.agricole.dto.CollectivityOverallStatistics;
import hei.school.agricole.service.StatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
public class StatisticsController {

    private final StatisticsService service;

    public StatisticsController(StatisticsService service) {
        this.service = service;
    }

    @GetMapping("/collectivites/{id}/statistics")
    public List<CollectivityLocalStatistics> getLocalStatistics(@PathVariable String id,
                                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return service.getMemberStatistics(id, from, to);
    }

    @GetMapping("/collectivites/statistics")
    public List<CollectivityOverallStatistics> getOverallStatistics(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return service.getAllCollectivitiesStatistics(from, to);
    }
}