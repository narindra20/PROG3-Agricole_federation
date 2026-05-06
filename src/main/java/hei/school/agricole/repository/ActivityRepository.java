package hei.school.agricole.repository;

import hei.school.agricole.config.DataSource;
import hei.school.agricole.entity.Activity;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActivityRepository {

    public Activity save(String collectivityId, Activity activity) {
        String sql = "INSERT INTO activity (collectivity_id, label, activity_type, member_occupation_concerned, executive_date) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(collectivityId));
            ps.setString(2, activity.getLabel());
            ps.setString(3, activity.getActivityType());
            Array array = conn.createArrayOf("varchar", activity.getMemberOccupationConcerned().toArray(new String[0]));
            ps.setArray(4, array);
            ps.setDate(5, Date.valueOf(activity.getExecutiveDate()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                activity.setId(String.valueOf(rs.getInt("id")));
            }
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
            ps.setInt(1, Integer.parseInt(collectivityId));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Activity a = new Activity();
                a.setId(String.valueOf(rs.getInt("id")));
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
            ps.setInt(1, Integer.parseInt(activityId));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Activity a = new Activity();
                a.setId(activityId);
                a.setCollectivityId(String.valueOf(rs.getInt("collectivity_id")));
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