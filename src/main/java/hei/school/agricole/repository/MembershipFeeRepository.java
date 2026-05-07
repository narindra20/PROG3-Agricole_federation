package hei.school.agricole.repository;

import hei.school.agricole.config.DataSource;
import hei.school.agricole.dto.CreateMembershipFee;
import hei.school.agricole.entity.MembershipFee;
import hei.school.agricole.enums.ActivityStatus;
import hei.school.agricole.enums.Frequency;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class MembershipFeeRepository {

    public List<MembershipFee> findByCollectivityId(String collectivityId) {
        String sql = "SELECT id, eligible_from, frequency, amount, label, status FROM membership_fee WHERE collectivity_id = ?";
        List<MembershipFee> list = new ArrayList<>();
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MembershipFee fee = new MembershipFee();
                fee.setId(rs.getString("id"));
                fee.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
                fee.setFrequency(Frequency.valueOf(rs.getString("frequency")));
                fee.setAmount(rs.getDouble("amount"));
                fee.setLabel(rs.getString("label"));
                fee.setStatus(ActivityStatus.valueOf(rs.getString("status")));
                list.add(fee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public MembershipFee findById(String id) {
        String sql = "SELECT id, eligible_from, frequency, amount, label, status FROM membership_fee WHERE id = ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                MembershipFee fee = new MembershipFee();
                fee.setId(rs.getString("id"));
                fee.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
                fee.setFrequency(Frequency.valueOf(rs.getString("frequency")));
                fee.setAmount(rs.getDouble("amount"));
                fee.setLabel(rs.getString("label"));
                fee.setStatus(ActivityStatus.valueOf(rs.getString("status")));
                return fee;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MembershipFee> saveAll(String collectivityId, List<CreateMembershipFee> fees) {
        String sql = "INSERT INTO membership_fee (id, collectivity_id, eligible_from, frequency, amount, label, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        List<MembershipFee> result = new ArrayList<>();
        try (Connection conn = DataSource.getConnection()) {
            for (CreateMembershipFee f : fees) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    String id = UUID.randomUUID().toString();
                    ps.setString(1, id);
                    ps.setString(2, collectivityId);
                    ps.setDate(3, Date.valueOf(f.getEligibleFrom()));
                    ps.setString(4, f.getFrequency().name());
                    ps.setDouble(5, f.getAmount());
                    ps.setString(6, f.getLabel());
                    ps.setString(7, ActivityStatus.ACTIVE.name());
                    ps.executeUpdate();
                    MembershipFee saved = new MembershipFee();
                    saved.setId(id);
                    saved.setEligibleFrom(f.getEligibleFrom());
                    saved.setFrequency(f.getFrequency());
                    saved.setAmount(f.getAmount());
                    saved.setLabel(f.getLabel());
                    saved.setStatus(ActivityStatus.ACTIVE);
                    result.add(saved);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public List<MembershipFee> findActiveFeesByCollectivityId(String collectivityId) {
        String sql = "SELECT id, eligible_from, frequency, amount, label, status FROM membership_fee WHERE collectivity_id = ? AND status = 'ACTIVE'";
        List<MembershipFee> fees = new ArrayList<>();
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MembershipFee fee = new MembershipFee();
                fee.setId(rs.getString("id"));
                fee.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
                fee.setFrequency(Frequency.valueOf(rs.getString("frequency")));
                fee.setAmount(rs.getDouble("amount"));
                fee.setLabel(rs.getString("label"));
                fee.setStatus(ActivityStatus.valueOf(rs.getString("status")));
                fees.add(fee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fees;
    }

    public List<MembershipFee> findByCollectivityIdAndStatus(String collectivityId, String status) {
        String sql = "SELECT id, eligible_from, frequency, amount, label, status FROM membership_fee WHERE collectivity_id = ? AND status = ?";
        List<MembershipFee> fees = new ArrayList<>();
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ps.setString(2, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MembershipFee fee = new MembershipFee();
                fee.setId(rs.getString("id"));
                fee.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
                fee.setFrequency(Frequency.valueOf(rs.getString("frequency")));
                fee.setAmount(rs.getDouble("amount"));
                fee.setLabel(rs.getString("label"));
                fee.setStatus(ActivityStatus.valueOf(rs.getString("status")));
                fees.add(fee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fees;
    }
}