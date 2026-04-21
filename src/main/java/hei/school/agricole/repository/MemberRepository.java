package hei.school.agricole.repository;

import hei.school.agricole.config.DataSource;
import hei.school.agricole.entity.Member;
import hei.school.agricole.enums.Gender;
import hei.school.agricole.enums.MemberOccupation;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {

    private final Connection connection;

    public MemberRepository() throws SQLException {
        this.connection = new DataSource().getConnection();
    }

    public Member save(Member m) throws SQLException {

        String sql = """
                INSERT INTO member
                (last_name, first_name, birth_date, gender, address,
                 phone, email, membership_date, occupation)
                VALUES (?, ?, ?, ?::gender_enum, ?, ?, ?, ?, ?::member_occupation)
                RETURNING id
                """;

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, m.getLastName());
        ps.setString(2, m.getFirstName());
        ps.setDate(3, m.getBirthDate() != null ? Date.valueOf(m.getBirthDate()) : null);
        ps.setObject(4, m.getGender() != null ? m.getGender().name() : null, Types.OTHER);
        ps.setString(5, m.getAddress());
        ps.setString(6, m.getPhone());
        ps.setString(7, m.getEmail());
        ps.setDate(8, m.getMembershipDate() != null ? Date.valueOf(m.getMembershipDate()) : null);
        ps.setString(9, m.getOccupation().name());

        try {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                m.setId(rs.getInt("id"));
            }
            return m;
        } catch (SQLException e) {
            if (e.getMessage().contains("member_phone_key")) {
                throw new RuntimeException("Phone already exists");
            }
            if (e.getMessage().contains("member_email_key")) {
                throw new RuntimeException("Email already exists");
            }
            throw e;
        }
    }

    public List<Member> findAll() throws SQLException {

        String sql = """
                SELECT id, last_name, first_name, birth_date, gender,
                       address, phone, email, membership_date, occupation
                FROM member
                """;

        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Member> list = new ArrayList<>();

        while (rs.next()) {
            list.add(mapRow(rs));
        }

        return list;
    }

    public Member findById(int id) throws SQLException {

        String sql = """
                SELECT id, last_name, first_name, birth_date, gender,
                       address, phone, email, membership_date, occupation
                FROM member
                WHERE id = ?
                """;

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return mapRow(rs);
        }

        return null;
    }

    public void delete(int id) throws SQLException {

        String sql = "DELETE FROM member WHERE id = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public boolean existsByPhone(String phone) throws SQLException {
        String sql = "SELECT 1 FROM member WHERE phone = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, phone);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public boolean existsByEmail(String email) throws SQLException {
        String sql = "SELECT 1 FROM member WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    private Member mapRow(ResultSet rs) throws SQLException {

        Member m = new Member();

        m.setId(rs.getInt("id"));
        m.setLastName(rs.getString("last_name"));
        m.setFirstName(rs.getString("first_name"));

        Date birth = rs.getDate("birth_date");
        if (birth != null) m.setBirthDate(birth.toLocalDate());

        String gender = rs.getString("gender");
        if (gender != null) m.setGender(Gender.valueOf(gender));

        m.setAddress(rs.getString("address"));
        m.setPhone(rs.getString("phone"));
        m.setEmail(rs.getString("email"));

        Date membership = rs.getDate("membership_date");
        if (membership != null) m.setMembershipDate(membership.toLocalDate());

        String occupation = rs.getString("occupation");
        if (occupation != null) m.setOccupation(MemberOccupation.valueOf(occupation));

        return m;
    }
}