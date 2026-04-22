package hei.school.agricole.repository;

import hei.school.agricole.config.DataSource;
import hei.school.agricole.entity.Account;

import java.sql.*;

public class AccountRepository {

    public Account findById(String id) {

        String sql = "SELECT * FROM account WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Account acc = new Account();
                acc.setId(id);
                acc.setType(rs.getString("type"));
                acc.setBalance(rs.getDouble("balance"));
                acc.setCollectivityId(String.valueOf(rs.getInt("collectivity_id")));
                return acc;
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBalance(String id, double balance) {

        String sql = "UPDATE account SET balance = ? WHERE id = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDouble(1, balance);
            ps.setInt(2, Integer.parseInt(id));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}