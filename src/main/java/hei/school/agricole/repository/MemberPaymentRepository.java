package hei.school.agricole.repository;

import hei.school.agricole.config.DataSource;
import hei.school.agricole.entity.MemberPayment;
import hei.school.agricole.enums.PaymentMode;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class MemberPaymentRepository {

    public MemberPayment save(String memberId, MemberPayment payment) {
        String sql = "INSERT INTO member_payment (id, member_id, amount, payment_mode, membership_fee_id, creation_date) VALUES (?, ?, ?, ?, ?, ?) RETURNING id, creation_date";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String id = UUID.randomUUID().toString();
            ps.setString(1, id);
            ps.setString(2, memberId);
            ps.setInt(3, payment.getAmount());
            ps.setString(4, payment.getPaymentMode().name());
            ps.setString(5, payment.getMembershipFeeId());
            ps.setDate(6, Date.valueOf(payment.getCreationDate()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                payment.setId(rs.getString("id"));
                payment.setCreationDate(rs.getDate("creation_date").toLocalDate());
            }
            return payment;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MemberPayment> findByMemberId(String memberId) {
        String sql = "SELECT id, amount, payment_mode, membership_fee_id, creation_date FROM member_payment WHERE member_id = ?";
        List<MemberPayment> payments = new ArrayList<>();
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, memberId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MemberPayment p = new MemberPayment();
                p.setId(rs.getString("id"));
                p.setAmount(rs.getInt("amount"));
                p.setPaymentMode(PaymentMode.valueOf(rs.getString("payment_mode")));
                p.setMembershipFeeId(rs.getString("membership_fee_id"));
                p.setCreationDate(rs.getDate("creation_date").toLocalDate());
                payments.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return payments;
    }

    public Double getTotalPaymentsByMemberAndFeeBetweenDates(String memberId, String feeId, LocalDate from, LocalDate to) {
        String sql = "SELECT COALESCE(SUM(amount), 0) FROM member_payment WHERE member_id = ? AND membership_fee_id = ? AND creation_date BETWEEN ? AND ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, memberId);
            ps.setString(2, feeId);
            ps.setDate(3, Date.valueOf(from));
            ps.setDate(4, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
            return 0.0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Double getTotalPaymentsByMemberBetweenDates(String memberId, LocalDate from, LocalDate to) {
        String sql = "SELECT COALESCE(SUM(amount), 0) FROM member_payment WHERE member_id = ? AND creation_date BETWEEN ? AND ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, memberId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
            return 0.0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}