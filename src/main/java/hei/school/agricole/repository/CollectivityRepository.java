package hei.school.agricole.repository;

import hei.school.agricole.config.DataSource;
import hei.school.agricole.entity.Collectivity;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CollectivityRepository {

    private final Connection connection;

    public CollectivityRepository() throws SQLException {
        this.connection = new DataSource().getConnection();
    }

    public Collectivity save(Collectivity c) {

        String sql = """
            INSERT INTO collectivity
            (location, creation_date, city_id, domain_id, federation_id, is_authorized)
            VALUES (?, ?, ?, ?, ?, ?)
            RETURNING id
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, c.getLocation());
            ps.setDate(2, Date.valueOf(c.getCreationDate()));

            ps.setInt(3, c.getCityId());
            ps.setInt(4, c.getDomainId());

            if (c.getFederationId() != null) {
                ps.setInt(5, c.getFederationId());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            ps.setBoolean(6, c.isAuthorized());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c.setId(rs.getInt("id"));
            }

            return c;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Collectivity findById(int id) {

        String sql = "SELECT * FROM collectivity WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Collectivity c = new Collectivity();

                c.setId(rs.getInt("id"));
                c.setLocation(rs.getString("location"));
                c.setName(rs.getString("name"));
                c.setNumber(rs.getString("number"));

                c.setCityId(rs.getInt("city_id"));
                c.setDomainId(rs.getInt("domain_id"));

                Object federationId = rs.getObject("federation_id");
                if (federationId != null) {
                    c.setFederationId(rs.getInt("federation_id"));
                }

                c.setAuthorized(rs.getBoolean("is_authorized"));

                return c;
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Collectivity updateInformations(Collectivity c) {

        String sql = """
            UPDATE collectivity
            SET name = ?, number = ?
            WHERE id = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getNumber());
            ps.setInt(3, c.getId());

            ps.executeUpdate();

            return c;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Collectivity> findAll() {

        String sql = "SELECT * FROM collectivity";

        List<Collectivity> list = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Collectivity c = new Collectivity();

                c.setId(rs.getInt("id"));
                c.setLocation(rs.getString("location"));
                c.setName(rs.getString("name"));
                c.setNumber(rs.getString("number"));

                c.setCityId(rs.getInt("city_id"));
                c.setDomainId(rs.getInt("domain_id"));

                Object federationId = rs.getObject("federation_id");
                if (federationId != null) {
                    c.setFederationId(rs.getInt("federation_id"));
                }

                c.setAuthorized(rs.getBoolean("is_authorized"));

                list.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
}