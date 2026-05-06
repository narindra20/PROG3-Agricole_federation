package hei.school.agricole.repository;

import hei.school.agricole.config.DataSource;
import hei.school.agricole.entity.CollectivityTransaction;
import hei.school.agricole.enums.PaymentMode;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CollectivityTransactionRepository {

    public void save(CollectivityTransaction tx) {
        String sql = "INSERT INTO collectivity_transaction (id, collectivity_id, creation_date, amount, payment_mode, account_credited_id, member_debited_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String id = UUID.randomUUID().toString();
            ps.setString(1, id);
            ps.setInt(2, Integer.parseInt(tx.getCollectivityId()));
            ps.setDate(3, Date.valueOf(tx.getCreationDate()));
            ps.setDouble(4, tx.getAmount());
            ps.setString(5, tx.getPaymentMode().name());
            ps.setInt(6, Integer.parseInt(tx.getAccountCreditedId()));
            ps.setInt(7, Integer.parseInt(tx.getMemberDebitedId()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CollectivityTransaction> findByCollectivityIdAndDateBetween(String collectivityId, LocalDate from, LocalDate to) {
        String sql = "SELECT id, creation_date, amount, payment_mode, account_credited_id, member_debited_id FROM collectivity_transaction WHERE collectivity_id = ? AND creation_date BETWEEN ? AND ? ORDER BY creation_date";
        List<CollectivityTransaction> list = new ArrayList<>();
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(collectivityId));
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CollectivityTransaction tx = new CollectivityTransaction();
                tx.setId(rs.getString("id"));
                tx.setCreationDate(rs.getDate("creation_date").toLocalDate());
                tx.setAmount(rs.getDouble("amount"));
                tx.setPaymentMode(PaymentMode.valueOf(rs.getString("payment_mode")));
                tx.setAccountCreditedId(String.valueOf(rs.getInt("account_credited_id")));
                tx.setMemberDebitedId(String.valueOf(rs.getInt("member_debited_id")));
                list.add(tx);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}