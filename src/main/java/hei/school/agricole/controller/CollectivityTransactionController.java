package hei.school.agricole.controller;

import hei.school.agricole.entity.CollectivityTransaction;
import hei.school.agricole.service.CollectivityTransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class CollectivityTransactionController {
    private final CollectivityTransactionService service;

    public CollectivityTransactionController(CollectivityTransactionService service) {
        this.service = service;
    }

    @GetMapping("/{id}/transactions")
    public List<CollectivityTransaction> getTransactions(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return service.getTransactions(id, from, to);
    }
}