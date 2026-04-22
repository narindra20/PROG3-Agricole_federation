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
            throw new RuntimeException("Failed to establish database connection", e);
        }
    }

    public Collectivity save(Collectivity c) {

        String sql = """
            INSERT INTO collectivity
            (number, name, creation_date, city_id, domain_id, federation_id, sector_id, is_authorized)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, c.getNumber());
            ps.setString(2, c.getName());
            ps.setDate(3, Date.valueOf(c.getCreationDate()));
            ps.setInt(4, c.getCityId());
            ps.setInt(5, c.getDomainId());

            if (c.getFederationId() != null) {
                ps.setInt(6, c.getFederationId());
            } else {
                ps.setNull(6, Types.INTEGER);
            }

            if (c.getSectorId() != null) {
                ps.setInt(7, c.getSectorId());
            } else {
                ps.setNull(7, Types.INTEGER);
            }

            ps.setBoolean(8, c.isAuthorized());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c.setId(rs.getInt("id"));
                }
            }

            return c;

        } catch (SQLException e) {
            throw new RuntimeException("Error while saving collectivity", e);
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
                c.setNumber(rs.getInt("number"));
                c.setName(rs.getString("name"));
                c.setCreationDate(rs.getDate("creation_date").toLocalDate());
                c.setCityId(rs.getInt("city_id"));
                c.setDomainId(rs.getInt("domain_id"));
                c.setFederationId(rs.getObject("federation_id", Integer.class));
                c.setSectorId(rs.getObject("sector_id", Integer.class));
                c.setAuthorized(rs.getBoolean("is_authorized"));

                list.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching collectivities", e);
        }

        return list;
    }
}