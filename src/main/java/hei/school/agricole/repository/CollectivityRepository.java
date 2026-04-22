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

    public Collectivity save(Collectivity c) throws SQLException {

        String sql = """
            INSERT INTO collectivity
            (location, creation_date, is_authorized)
            VALUES (?, ?, ?)
            RETURNING id
        """;

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, c.getLocation());
        ps.setDate(2, Date.valueOf(c.getCreationDate()));
        ps.setBoolean(3, c.isAuthorized());

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            c.setId(rs.getInt("id"));
        }

        return c;
    }

    public List<Collectivity> findAll() throws SQLException {

        String sql = "SELECT * FROM collectivity";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Collectivity> list = new ArrayList<>();

        while (rs.next()) {
            Collectivity c = new Collectivity();

            c.setId(rs.getInt("id"));
            c.setLocation(rs.getString("location"));
            c.setCreationDate(rs.getDate("creation_date").toLocalDate());
            c.setAuthorized(rs.getBoolean("is_authorized"));

            list.add(c);
        }

        return list;
    }
}