package hei.school.agricole.repository;

import hei.school.agricole.config.DataSource;
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
                collectivity.setId(String.valueOf(rs.getInt("id")));
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
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Collectivity c = new Collectivity();
                c.setId(String.valueOf(rs.getInt("id")));
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
                c.setId(String.valueOf(rs.getInt("id")));
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
            ps.setInt(1, Integer.parseInt(id));
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
            ps.setInt(3, Integer.parseInt(id));
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
                ids.add(String.valueOf(rs.getInt("id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ids;
    }
}