package hei.school.agricole.controller;

import hei.school.agricole.dto.CreateMembershipFee;
import hei.school.agricole.entity.MembershipFee;
import hei.school.agricole.service.MembershipFeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class MembershipFeeController {

    private final MembershipFeeService service;

    public MembershipFeeController(MembershipFeeService service) {
        this.service = service;
    }

    @GetMapping("/{id}/membershipFees")
    public List<MembershipFee> getFees(@PathVariable String id) {
        return service.getByCollectivity(id);
    }

    @PostMapping("/{id}/membershipFees")
    public List<MembershipFee> createFees(
            @PathVariable String id,
            @RequestBody List<CreateMembershipFee> fees
    ) {
        return service.create(id, fees);
    }
}