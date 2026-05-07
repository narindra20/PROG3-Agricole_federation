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
        String id = java.util.UUID.randomUUID().toString();
        String sql = "INSERT INTO collectivity (id, name, number, location, creation_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, collectivity.getName());
            ps.setString(3, collectivity.getNumber());
            ps.setString(4, collectivity.getLocation());
            ps.setDate(5, Date.valueOf(collectivity.getCreationDate()));
            ps.executeUpdate();
            collectivity.setId(id);
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
        SELECT c.id,
               c.name,
               c.number,
               COALESCE(nm.new_count, 0) AS new_members,
               COALESCE(pct.pct, 0) AS pct_up_to_date
        FROM collectivity c
        LEFT JOIN (
            SELECT collectivity_id, COUNT(*) AS new_count
            FROM member
            WHERE membership_date BETWEEN ? AND ?
            GROUP BY collectivity_id
        ) nm ON nm.collectivity_id = c.id
        LEFT JOIN (
            SELECT mp.collectivity_id,
                   100.0 * COUNT(CASE WHEN mp.total_paid >= af.total_due THEN 1 END) / COUNT(*) AS pct
            FROM (
                SELECT m.collectivity_id, m.id AS member_id, COALESCE(SUM(mp.amount), 0) AS total_paid
                FROM member m
                LEFT JOIN member_payment mp ON mp.member_id = m.id AND mp.creation_date BETWEEN ? AND ?
                GROUP BY m.collectivity_id, m.id
            ) mp
            LEFT JOIN (
                SELECT collectivity_id, SUM(amount) AS total_due
                FROM membership_fee
                WHERE status = 'ACTIVE' AND eligible_from <= ?
                GROUP BY collectivity_id
            ) af ON af.collectivity_id = mp.collectivity_id
            GROUP BY mp.collectivity_id
        ) pct ON pct.collectivity_id = c.id
        ORDER BY c.id
    """;
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int idx = 1;
            ps.setDate(idx++, Date.valueOf(from));
            ps.setDate(idx++, Date.valueOf(to));
            ps.setDate(idx++, Date.valueOf(from));
            ps.setDate(idx++, Date.valueOf(to));
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
                stat.setOverallMemberAssiduityPercentage(0.0);
                list.add(stat);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}