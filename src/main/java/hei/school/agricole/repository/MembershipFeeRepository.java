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

@Repository
public class MembershipFeeRepository {

    private final Connection connection;

    public MembershipFeeRepository() throws SQLException {
        this.connection = new DataSource().getConnection();
    }

    public List<MembershipFee> findByCollectivityId(String collectivityId) {

        String sql = "SELECT * FROM membership_fee WHERE collectivity_id = ?";

        List<MembershipFee> list = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(collectivityId));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MembershipFee fee = new MembershipFee();

                fee.setId(String.valueOf(rs.getInt("id")));
                fee.setEligibleFrom(LocalDate.parse(rs.getDate("eligible_from").toString()));
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

    public List<MembershipFee> saveAll(String collectivityId, List<CreateMembershipFee> fees) {

        String sql = """
            INSERT INTO membership_fee
            (collectivity_id, eligible_from, frequency, amount, label, status)
            VALUES (?, ?, ?, ?, ?, ?)
            RETURNING id
        """;

        List<MembershipFee> result = new ArrayList<>();

        try {

            for (CreateMembershipFee f : fees) {

                try (PreparedStatement ps = connection.prepareStatement(sql)) {

                    ps.setInt(1, Integer.parseInt(collectivityId));
                    ps.setDate(2, Date.valueOf(f.getEligibleFrom()));
                    ps.setString(3, String.valueOf(f.getFrequency()));
                    ps.setDouble(4, f.getAmount());
                    ps.setString(5, f.getLabel());
                    ps.setString(6, "ACTIVE");

                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        MembershipFee saved = new MembershipFee();

                        saved.setId(String.valueOf(rs.getInt("id")));
                        saved.setEligibleFrom(LocalDate.parse(f.getEligibleFrom()));
                        saved.setFrequency(f.getFrequency());
                        saved.setAmount(f.getAmount());
                        saved.setLabel(f.getLabel());
                        saved.setStatus(ActivityStatus.valueOf("ACTIVE"));

                        result.add(saved);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}