package hei.school.agricole.repository;

import hei.school.agricole.config.DataSource;
import hei.school.agricole.entity.MemberPayment;
import hei.school.agricole.enums.PaymentMode;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDate;

@Repository
public class MemberPaymentRepository {

    public MemberPayment save(String memberId, MemberPayment payment) {
        String sql = "INSERT INTO member_payment (member_id, amount, payment_mode, membership_fee_id, creation_date) VALUES (?, ?, ?, ?, ?) RETURNING id, creation_date";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(memberId));
            ps.setInt(2, payment.getAmount());
            ps.setString(3, payment.getPaymentMode().name());
            ps.setInt(4, Integer.parseInt(payment.getMembershipFeeId()));
            ps.setDate(5, Date.valueOf(payment.getCreationDate()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                payment.setId(String.valueOf(rs.getInt("id")));
                payment.setCreationDate(rs.getDate("creation_date").toLocalDate());
            }
            return payment;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}