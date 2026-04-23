package hei.school.agricole.repository;

import hei.school.agricole.config.DataSource;
import hei.school.agricole.entity.Member;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {

    public boolean existsByPhone(String phone) {
        String sql = "SELECT 1 FROM member WHERE phone = ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM member WHERE email = ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Member save(Member member) {
        String sql = "INSERT INTO member (first_name, last_name, collectivity_id, phone, email, birth_date, gender, address, profession, occupation, membership_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, member.getFirstName());
            ps.setString(2, member.getLastName());
            ps.setInt(3, Integer.parseInt(member.getCollectivityId()));
            ps.setString(4, member.getPhone());
            ps.setString(5, member.getEmail());
            ps.setDate(6, member.getBirthDate() != null ? Date.valueOf(member.getBirthDate()) : null);
            ps.setString(7, member.getGender() != null ? member.getGender().name() : null);
            ps.setString(8, member.getAddress());
            ps.setString(9, member.getProfession());
            ps.setString(10, member.getOccupation() != null ? member.getOccupation().name() : null);
            ps.setDate(11, member.getMembershipDate() != null ? Date.valueOf(member.getMembershipDate()) : null);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                member.setId(String.valueOf(rs.getInt("id")));
            }
            return member;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Member> findAll() {
        String sql = "SELECT id, first_name, last_name, collectivity_id, phone, email, birth_date, gender, address, profession, occupation, membership_date FROM member";
        List<Member> members = new ArrayList<>();
        try (Connection conn = DataSource.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                members.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return members;
    }

    public Member findById(String id) {
        String sql = "SELECT id, first_name, last_name, collectivity_id, phone, email, birth_date, gender, address, profession, occupation, membership_date FROM member WHERE id = ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM member WHERE id = ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Member mapRow(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setId(String.valueOf(rs.getInt("id")));
        member.setFirstName(rs.getString("first_name"));
        member.setLastName(rs.getString("last_name"));
        member.setCollectivityId(String.valueOf(rs.getInt("collectivity_id")));
        member.setPhone(rs.getString("phone"));
        member.setEmail(rs.getString("email"));
        Date birthDate = rs.getDate("birth_date");

        if (birthDate != null) member.setBirthDate(birthDate.toLocalDate());
        String genderStr = rs.getString("gender");
        if (genderStr != null) member.setGender(hei.school.agricole.enums.Gender.valueOf(genderStr));
        member.setAddress(rs.getString("address"));
        member.setProfession(rs.getString("profession"));
        String occupationStr = rs.getString("occupation");
        if (occupationStr != null) member.setOccupation(hei.school.agricole.enums.MemberOccupation.valueOf(occupationStr));
        Date membershipDate = rs.getDate("membership_date");
        if (membershipDate != null) member.setMembershipDate(membershipDate.toLocalDate());
        return member;
    }

    public List<Member> findByCollectivityId(String collectivityId) {
        String sql = "SELECT id, first_name, last_name, collectivity_id, phone, email, birth_date, gender, address, profession, occupation, membership_date FROM member WHERE collectivity_id = ?";
        List<Member> members = new ArrayList<>();
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(collectivityId));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                members.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return members;
    }
}