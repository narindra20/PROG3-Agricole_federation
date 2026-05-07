package hei.school.agricole.repository;

import hei.school.agricole.config.DataSource;
import hei.school.agricole.dto.CollectivityLocalStatistics;
import hei.school.agricole.dto.MemberDescription;
import hei.school.agricole.entity.Member;
import hei.school.agricole.enums.Gender;
import hei.school.agricole.enums.MemberOccupation;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        String id = UUID.randomUUID().toString();
        String sql = "INSERT INTO member (id, first_name, last_name, collectivity_id, phone, email, birth_date, gender, address, profession, occupation, membership_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, member.getFirstName());
            ps.setString(3, member.getLastName());
            ps.setString(4, member.getCollectivityId());
            ps.setString(5, member.getPhone());
            ps.setString(6, member.getEmail());
            ps.setDate(7, member.getBirthDate() != null ? Date.valueOf(member.getBirthDate()) : null);
            ps.setString(8, member.getGender() != null ? member.getGender().name() : null);
            ps.setString(9, member.getAddress());
            ps.setString(10, member.getProfession());
            ps.setString(11, member.getOccupation() != null ? member.getOccupation().name() : null);
            ps.setDate(12, member.getMembershipDate() != null ? Date.valueOf(member.getMembershipDate()) : null);
            ps.executeUpdate();
            member.setId(id);
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
            ps.setString(1, id);
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
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Member> findByCollectivityId(String collectivityId) {
        String sql = "SELECT id, first_name, last_name, collectivity_id, phone, email, birth_date, gender, address, profession, occupation, membership_date FROM member WHERE collectivity_id = ?";
        List<Member> members = new ArrayList<>();
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                members.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return members;
    }

    public List<Member> findActiveMembersByCollectivityId(String collectivityId) {
        return findByCollectivityId(collectivityId);
    }

    public int countNewMembersByCollectivityIdBetweenDates(String collectivityId, LocalDate from, LocalDate to) {
        String sql = "SELECT COUNT(*) FROM member WHERE collectivity_id = ? AND membership_date BETWEEN ? AND ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CollectivityLocalStatistics> getLocalStatistics(String collectivityId, LocalDate from, LocalDate to) {
        String sql = """
            WITH active_fees AS (
                SELECT SUM(amount) AS total_due
                FROM membership_fee
                WHERE collectivity_id = ? AND status = 'ACTIVE' AND eligible_from <= ?
            ),
            mandatory_activities AS (
                SELECT COUNT(*) AS total_mandatory
                FROM activity
                WHERE collectivity_id = ? AND activity_type IN ('MEETING', 'TRAINING')
                  AND executive_date BETWEEN ? AND ?
            ),
            member_payments AS (
                SELECT mp.member_id, COALESCE(SUM(mp.amount), 0) AS total_paid
                FROM member_payment mp
                WHERE mp.creation_date BETWEEN ? AND ?
                GROUP BY mp.member_id
            ),
            member_attendance AS (
                SELECT att.member_id,
                       COUNT(CASE WHEN att.attendance_status = 'ATTENDED' THEN 1 END) AS attended_count
                FROM attendance att
                JOIN activity a ON att.activity_id = a.id
                WHERE a.collectivity_id = ? AND a.executive_date BETWEEN ? AND ?
                  AND a.activity_type IN ('MEETING', 'TRAINING')
                GROUP BY att.member_id
            )
            SELECT m.id,
                   m.first_name,
                   m.last_name,
                   m.email,
                   m.occupation,
                   COALESCE(mp.total_paid, 0) AS earned_amount,
                   GREATEST(COALESCE(af.total_due, 0) - COALESCE(mp.total_paid, 0), 0) AS unpaid_amount,
                   CASE
                       WHEN ma.total_mandatory = 0 THEN 100.0
                       ELSE COALESCE(matt.attended_count, 0) * 100.0 / ma.total_mandatory
                   END AS assiduity_percentage
            FROM member m
            CROSS JOIN (SELECT COALESCE(MAX(total_due), 0) AS total_due FROM active_fees) af
            CROSS JOIN (SELECT COALESCE(MAX(total_mandatory), 0) AS total_mandatory FROM mandatory_activities) ma
            LEFT JOIN member_payments mp ON mp.member_id = m.id
            LEFT JOIN member_attendance matt ON matt.member_id = m.id
            WHERE m.collectivity_id = ?
            ORDER BY m.id
        """;
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int idx = 1;
            ps.setString(idx++, collectivityId);
            ps.setDate(idx++, Date.valueOf(to));
            ps.setString(idx++, collectivityId);
            ps.setDate(idx++, Date.valueOf(from));
            ps.setDate(idx++, Date.valueOf(to));
            ps.setDate(idx++, Date.valueOf(from));
            ps.setDate(idx++, Date.valueOf(to));
            ps.setString(idx++, collectivityId);
            ps.setDate(idx++, Date.valueOf(from));
            ps.setDate(idx++, Date.valueOf(to));
            ps.setString(idx, collectivityId);

            ResultSet rs = ps.executeQuery();
            List<CollectivityLocalStatistics> list = new ArrayList<>();
            while (rs.next()) {
                CollectivityLocalStatistics stat = new CollectivityLocalStatistics();
                MemberDescription desc = new MemberDescription();
                desc.setId(rs.getString("id"));
                desc.setFirstName(rs.getString("first_name"));
                desc.setLastName(rs.getString("last_name"));
                desc.setEmail(rs.getString("email"));
                desc.setOccupation(rs.getString("occupation"));
                stat.setMemberDescription(desc);
                stat.setEarnedAmount(rs.getDouble("earned_amount"));
                stat.setUnpaidAmount(rs.getDouble("unpaid_amount"));
                stat.setAssiduityPercentage(rs.getDouble("assiduity_percentage"));
                list.add(stat);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Member mapRow(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setId(rs.getString("id"));
        member.setFirstName(rs.getString("first_name"));
        member.setLastName(rs.getString("last_name"));
        member.setCollectivityId(rs.getString("collectivity_id"));
        member.setPhone(rs.getString("phone"));
        member.setEmail(rs.getString("email"));
        Date birthDate = rs.getDate("birth_date");
        if (birthDate != null) member.setBirthDate(birthDate.toLocalDate());
        String genderStr = rs.getString("gender");
        if (genderStr != null) member.setGender(Gender.valueOf(genderStr));
        member.setAddress(rs.getString("address"));
        member.setProfession(rs.getString("profession"));
        String occupationStr = rs.getString("occupation");
        if (occupationStr != null) member.setOccupation(MemberOccupation.valueOf(occupationStr));
        Date membershipDate = rs.getDate("membership_date");
        if (membershipDate != null) member.setMembershipDate(membershipDate.toLocalDate());
        return member;
    }
}