package hei.school.agricole.repository;

import hei.school.agricole.config.DataSource;
import hei.school.agricole.entity.*;
import hei.school.agricole.enums.Bank;
import hei.school.agricole.enums.MobileBankingService;
import org.springframework.stereotype.Repository;
import java.sql.*;

@Repository
public class FinancialAccountRepository {

    public FinancialAccount findById(String id) {
        String sql = "SELECT * FROM financial_account WHERE id = ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void increaseBalance(String accountId, double amount) {
        String sql = "UPDATE financial_account SET amount = amount + ? WHERE id = ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setInt(2, Integer.parseInt(accountId));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public FinancialAccount findFederationAccount() {
        String sql = "SELECT * FROM financial_account WHERE collectivity_id IS NULL LIMIT 1";
        try (Connection conn = DataSource.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return mapRow(rs);
            }
            throw new RuntimeException("Federation account not configured");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private FinancialAccount mapRow(ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        String id = String.valueOf(rs.getInt("id"));
        double amount = rs.getDouble("amount");
        String collectivityId = rs.getString("collectivity_id");

        if ("CASH".equals(type)) {
            CashAccount acc = new CashAccount();
            acc.setId(id);
            acc.setAmount(amount);
            acc.setCollectivityId(collectivityId);
            return acc;
        } else if ("MOBILE".equals(type)) {
            MobileBankingAccount acc = new MobileBankingAccount();
            acc.setId(id);
            acc.setAmount(amount);
            acc.setCollectivityId(collectivityId);
            acc.setHolderName(rs.getString("holder_name"));
            acc.setMobileBankingService(MobileBankingService.valueOf(rs.getString("mobile_service")));
            acc.setMobileNumber(rs.getString("mobile_number"));
            return acc;
        } else if ("BANK".equals(type)) {
            BankAccount acc = new BankAccount();
            acc.setId(id);
            acc.setAmount(amount);
            acc.setCollectivityId(collectivityId);
            acc.setHolderName(rs.getString("holder_name"));
            acc.setBankName(Bank.valueOf(rs.getString("bank_name")));
            acc.setBankCode(rs.getInt("bank_code"));
            acc.setBankBranchCode(rs.getInt("branch_code"));
            acc.setBankAccountNumber(rs.getString("account_number"));
            acc.setBankAccountKey(rs.getInt("rib_key"));
            return acc;
        }
        throw new IllegalStateException("Unknown account type: " + type);
    }
}