package hei.school.agricole.controller;

import hei.school.agricole.dto.CreateMemberPayment;
import hei.school.agricole.entity.MemberPayment;
import hei.school.agricole.service.MemberPaymentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberPaymentController {
    private final MemberPaymentService service;

    public MemberPaymentController(MemberPaymentService service) {
        this.service = service;
    }

    @PostMapping("/{id}/payments")
    public List<MemberPayment> createPayments(@PathVariable String id, @RequestBody List<CreateMemberPayment> requests) {
        return service.createPayments(id, requests);
    }
}