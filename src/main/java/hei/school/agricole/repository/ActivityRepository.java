package hei.school.agricole.repository;

import hei.school.agricole.config.DataSource;
import hei.school.agricole.entity.Activity;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ActivityRepository {

    public Activity save(String collectivityId, Activity activity) {
        String sql = "INSERT INTO activity (id, collectivity_id, label, activity_type, member_occupation_concerned, executive_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String id = UUID.randomUUID().toString();
            ps.setString(1, id);
            ps.setString(2, collectivityId);
            ps.setString(3, activity.getLabel());
            ps.setString(4, activity.getActivityType());
            Array array = conn.createArrayOf("varchar", activity.getMemberOccupationConcerned().toArray(new String[0]));
            ps.setArray(5, array);
            ps.setDate(6, Date.valueOf(activity.getExecutiveDate()));
            ps.executeUpdate();
            activity.setId(id);
            return activity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Activity> findByCollectivityId(String collectivityId) {
        String sql = "SELECT id, label, activity_type, member_occupation_concerned, executive_date FROM activity WHERE collectivity_id = ? ORDER BY executive_date";
        List<Activity> list = new ArrayList<>();
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Activity a = new Activity();
                a.setId(rs.getString("id"));
                a.setLabel(rs.getString("label"));
                a.setActivityType(rs.getString("activity_type"));
                Array array = rs.getArray("member_occupation_concerned");
                if (array != null) {
                    String[] occupations = (String[]) array.getArray();
                    a.setMemberOccupationConcerned(List.of(occupations));
                }
                a.setExecutiveDate(rs.getDate("executive_date").toLocalDate());
                list.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Activity findById(String activityId) {
        String sql = "SELECT collectivity_id, label, activity_type, member_occupation_concerned, executive_date FROM activity WHERE id = ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, activityId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Activity a = new Activity();
                a.setId(activityId);
                a.setCollectivityId(rs.getString("collectivity_id"));
                a.setLabel(rs.getString("label"));
                a.setActivityType(rs.getString("activity_type"));
                Array array = rs.getArray("member_occupation_concerned");
                if (array != null) {
                    String[] occupations = (String[]) array.getArray();
                    a.setMemberOccupationConcerned(List.of(occupations));
                }
                a.setExecutiveDate(rs.getDate("executive_date").toLocalDate());
                return a;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}