package hei.school.agricole.service;

import hei.school.agricole.dto.CreateMember;
import hei.school.agricole.entity.Member;
import hei.school.agricole.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService() throws SQLException {
        this.memberRepository = new MemberRepository();
    }

    public Member createFromDto(CreateMember dto) {

        try {

            if (memberRepository.existsByPhone(dto.getPhone())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone already exists");
            }

            if (memberRepository.existsByEmail(dto.getEmail())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
            }

            if (!dto.isRegistrationFeePaid() || !dto.isMembershipDuesPaid()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Membership dues not paid or registration fee not paid");
            }

            Member member = new Member();
            member.setFirstName(dto.getFirstName());
            member.setLastName(dto.getLastName());
            member.setBirthDate(dto.getBirthDate());
            member.setGender(dto.getGender());
            member.setOccupation(dto.getOccupation());
            member.setAddress(dto.getAddress());
            member.setPhone(dto.getPhone());
            member.setEmail(dto.getEmail());
            member.setMembershipDate(dto.getMembershipDate());
            member.setProfession(dto.getProfession());

            return memberRepository.save(member);

        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    public List<Member> findAll() {
        try {
            return memberRepository.findAll();
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    public Member findById(int id) {
        try {
            Member member = memberRepository.findById(id);

            if (member == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found");
            }

            return member;

        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    public void delete(int id) {
        try {
            memberRepository.delete(id);
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error");
        }
    }
}