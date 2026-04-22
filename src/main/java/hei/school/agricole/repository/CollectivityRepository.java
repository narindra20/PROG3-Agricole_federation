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

    public CollectivityRepository() {
        try {
            this.connection = new DataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Collectivity save(Collectivity c) {

        String sql = """
            INSERT INTO collectivity
            (location, creation_date, city_id, domain_id, federation_id, sector_id, is_authorized)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            RETURNING id
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, c.getLocation());
            ps.setDate(2, Date.valueOf(c.getCreationDate()));
            ps.setInt(3, c.getCityId());
            ps.setInt(4, c.getDomainId());

            if (c.getFederationId() != null)
                ps.setInt(5, c.getFederationId());
            else
                ps.setNull(5, Types.INTEGER);

            if (c.getSectorId() != null)
                ps.setInt(6, c.getSectorId());
            else
                ps.setNull(6, Types.INTEGER);

            ps.setBoolean(7, c.isAuthorized());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c.setId(rs.getInt("id"));
            }

            return c;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsByNumberOrName(String number, String name) {

        String sql = "SELECT 1 FROM collectivity WHERE number = ? OR name = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, number);
            ps.setString(2, name);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Collectivity updateIdentity(String id, String number, String name) {

        String sql = """
            UPDATE collectivity
            SET number = ?, name = ?
            WHERE id = ?
            RETURNING *
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, number);
            ps.setString(2, name);
            ps.setInt(3, Integer.parseInt(id));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Collectivity c = new Collectivity();

                c.setId(rs.getInt("id"));
                c.setNumber(rs.getString("number"));
                c.setName(rs.getString("name"));
                c.setLocation(rs.getString("location"));
                c.setCreationDate(rs.getDate("creation_date").toLocalDate());
                c.setCityId(rs.getInt("city_id"));
                c.setDomainId(rs.getInt("domain_id"));
                c.setFederationId(rs.getObject("federation_id", Integer.class));
                c.setSectorId(rs.getObject("sector_id", Integer.class));
                c.setAuthorized(rs.getBoolean("is_authorized"));

                return c;
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Collectivity> findAll() {

        String sql = "SELECT * FROM collectivity";

        List<Collectivity> list = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Collectivity c = new Collectivity();

                c.setId(rs.getInt("id"));
                c.setNumber(rs.getString("number"));
                c.setName(rs.getString("name"));
                c.setLocation(rs.getString("location"));
                c.setCreationDate(rs.getDate("creation_date").toLocalDate());
                c.setCityId(rs.getInt("city_id"));
                c.setDomainId(rs.getInt("domain_id"));
                c.setFederationId(rs.getObject("federation_id", Integer.class));
                c.setSectorId(rs.getObject("sector_id", Integer.class));
                c.setAuthorized(rs.getBoolean("is_authorized"));

                list.add(c);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }
}