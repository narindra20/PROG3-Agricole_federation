package hei.school.agricole.repository;

import hei.school.agricole.config.DataSource;
import hei.school.agricole.dto.CollectivityInformation;
import hei.school.agricole.dto.CollectivityOverallStatistics;
import hei.school.agricole.entity.Collectivity;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CollectivityRepository {

    public Collectivity save(Collectivity collectivity) {
        String sql = "INSERT INTO collectivity (name, number, location, creation_date) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collectivity.getName());
            ps.setString(2, collectivity.getNumber());
            ps.setString(3, collectivity.getLocation());
            ps.setDate(4, Date.valueOf(collectivity.getCreationDate()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                collectivity.setId(rs.getString("id"));
            }
            return collectivity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Collectivity findById(String id) {
        String sql = "SELECT id, name, number, location, creation_date FROM collectivity WHERE id = ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Collectivity c = new Collectivity();
                c.setId(rs.getString("id"));
                c.setName(rs.getString("name"));
                c.setNumber(rs.getString("number"));
                c.setLocation(rs.getString("location"));
                c.setCreationDate(rs.getDate("creation_date").toLocalDate());
                return c;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Collectivity> findAll() {
        String sql = "SELECT id, name, number, location, creation_date FROM collectivity";
        List<Collectivity> list = new ArrayList<>();
        try (Connection conn = DataSource.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Collectivity c = new Collectivity();
                c.setId(rs.getString("id"));
                c.setName(rs.getString("name"));
                c.setNumber(rs.getString("number"));
                c.setLocation(rs.getString("location"));
                c.setCreationDate(rs.getDate("creation_date").toLocalDate());
                list.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean existsById(String id) {
        String sql = "SELECT 1 FROM collectivity WHERE id = ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsByName(String name) {
        String sql = "SELECT 1 FROM collectivity WHERE name = ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsByNumber(String number) {
        String sql = "SELECT 1 FROM collectivity WHERE number = ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, number);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateInformation(String id, String name, String number) {
        String sql = "UPDATE collectivity SET name = ?, number = ? WHERE id = ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, number);
            ps.setString(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> findAllIds() {
        String sql = "SELECT id FROM collectivity";
        List<String> ids = new ArrayList<>();
        try (Connection conn = DataSource.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                ids.add(rs.getString("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ids;
    }

    public List<CollectivityOverallStatistics> getOverallStatistics(LocalDate from, LocalDate to) {
        String sql = """
            WITH active_fees_per_collectivity AS (
                SELECT collectivity_id, SUM(amount) AS total_due
                FROM membership_fee
                WHERE status = 'ACTIVE' AND eligible_from <= ?
                GROUP BY collectivity_id
            ),
            member_payments_per_collectivity AS (
                SELECT m.collectivity_id, mp.member_id, COALESCE(SUM(mp.amount), 0) AS paid
                FROM member_payment mp
                JOIN member m ON mp.member_id = m.id
                WHERE mp.creation_date BETWEEN ? AND ?
                GROUP BY m.collectivity_id, mp.member_id
            ),
            members_up_to_date AS (
                SELECT p.collectivity_id,
                       COUNT(p.member_id) AS up_to_date_count
                FROM member_payments_per_collectivity p
                JOIN active_fees_per_collectivity a ON p.collectivity_id = a.collectivity_id
                WHERE p.paid >= a.total_due
                GROUP BY p.collectivity_id
            ),
            total_members AS (
                SELECT collectivity_id, COUNT(*) AS total
                FROM member
                GROUP BY collectivity_id
            ),
            new_members AS (
                SELECT collectivity_id, COUNT(*) AS new_count
                FROM member
                WHERE membership_date BETWEEN ? AND ?
                GROUP BY collectivity_id
            ),
            mandatory_activities_count AS (
                SELECT collectivity_id, COUNT(*) AS total_mandatory
                FROM activity
                WHERE activity_type IN ('MEETING', 'TRAINING')
                  AND executive_date BETWEEN ? AND ?
                GROUP BY collectivity_id
            ),
            attendance_rate_per_collectivity AS (
                SELECT a.collectivity_id,
                       AVG(CASE WHEN att.attendance_status = 'ATTENDED' THEN 1.0 ELSE 0.0 END) * 100 AS avg_attendance
                FROM attendance att
                JOIN activity a ON att.activity_id = a.id
                WHERE a.activity_type IN ('MEETING', 'TRAINING')
                  AND a.executive_date BETWEEN ? AND ?
                GROUP BY a.collectivity_id
            )
            SELECT c.id,
                   c.name,
                   c.number,
                   COALESCE(nm.new_count, 0) AS new_members,
                   COALESCE(mut.up_to_date_count, 0) * 100.0 / NULLIF(tm.total, 0) AS pct_up_to_date,
                   COALESCE(ar.avg_attendance, 0) AS avg_attendance
            FROM collectivity c
            LEFT JOIN total_members tm ON tm.collectivity_id = c.id
            LEFT JOIN active_fees_per_collectivity af ON af.collectivity_id = c.id
            LEFT JOIN members_up_to_date mut ON mut.collectivity_id = c.id
            LEFT JOIN new_members nm ON nm.collectivity_id = c.id
            LEFT JOIN mandatory_activities_count mac ON mac.collectivity_id = c.id
            LEFT JOIN attendance_rate_per_collectivity ar ON ar.collectivity_id = c.id
            WHERE tm.total > 0
            ORDER BY c.id
        """;
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int idx = 1;
            ps.setDate(idx++, Date.valueOf(to));
            ps.setDate(idx++, Date.valueOf(from));
            ps.setDate(idx++, Date.valueOf(to));
            ps.setDate(idx++, Date.valueOf(from));
            ps.setDate(idx++, Date.valueOf(to));
            ps.setDate(idx++, Date.valueOf(from));
            ps.setDate(idx++, Date.valueOf(to));
            ps.setDate(idx++, Date.valueOf(from));
            ps.setDate(idx++, Date.valueOf(to));

            ResultSet rs = ps.executeQuery();
            List<CollectivityOverallStatistics> list = new ArrayList<>();
            while (rs.next()) {
                CollectivityOverallStatistics stat = new CollectivityOverallStatistics();
                CollectivityInformation info = new CollectivityInformation();
                info.setName(rs.getString("name"));
                info.setNumber(rs.getString("number"));
                stat.setCollectivityInformation(info);
                stat.setNewMembersNumber(rs.getInt("new_members"));
                stat.setOverallMemberCurrentDuePercentage(rs.getDouble("pct_up_to_date"));
                stat.setOverallMemberAssiduityPercentage(rs.getDouble("avg_attendance"));
                list.add(stat);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}