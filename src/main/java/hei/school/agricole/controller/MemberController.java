package hei.school.agricole.controller;

import hei.school.agricole.dto.CreateMember;
import hei.school.agricole.entity.Member;
import hei.school.agricole.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<List<Member>> create(@RequestBody List<CreateMember> requests) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(requests.stream()
                        .map(memberService::createFromDto)
                        .toList());
    }

    @GetMapping
    public List<Member> getAll() {
        return memberService.findAll();
    }

    @GetMapping("/{id}")
    public Member getById(@PathVariable String id) {
        Member m = memberService.findById(id);
        if (m == null) {
            throw new RuntimeException("Member not found");
        }
        return m;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}