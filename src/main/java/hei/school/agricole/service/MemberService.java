package hei.school.agricole.service;

import hei.school.agricole.dto.CreateMember;
import hei.school.agricole.entity.Member;
import hei.school.agricole.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createFromDto(CreateMember dto) {
        if (memberRepository.existsByPhone(dto.getPhone())) {
            throw new RuntimeException("Phone already exists");
        }
        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (!dto.isRegistrationFeePaid() || !dto.isMembershipDuesPaid()) {
            throw new RuntimeException("Membership dues not paid or registration fee not paid");
        }

        Member member = new Member();
        member.setFirstName(dto.getFirstName());
        member.setLastName(dto.getLastName());
        member.setCollectivityId(dto.getCollectivityIdentifier());
        member.setPhone(dto.getPhone());
        member.setEmail(dto.getEmail());
        member.setBirthDate(dto.getBirthDate());
        member.setGender(dto.getGender());
        member.setAddress(dto.getAddress());
        member.setProfession(dto.getProfession());
        member.setOccupation(dto.getOccupation());
        member.setMembershipDate(dto.getMembershipDate());

        return memberRepository.save(member);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(String id) {
        Member member = memberRepository.findById(id);
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found");
        }
        return member;
    }

    public void delete(String id) {
        memberRepository.delete(id);
    }
}