package hei.school.agricole.repository;

import hei.school.agricole.config.DataSource;
import hei.school.agricole.entity.Attendance;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AttendanceRepository {

    public void saveAll(String activityId, List<Attendance> attendances) {
        String sql = "INSERT INTO attendance (activity_id, member_id, attendance_status) VALUES (?, ?, ?)";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Attendance a : attendances) {
                ps.setInt(1, Integer.parseInt(activityId));
                ps.setInt(2, Integer.parseInt(a.getMemberId()));
                ps.setString(3, a.getAttendanceStatus());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Attendance> findByActivityId(String activityId) {
        String sql = "SELECT id, member_id, attendance_status FROM attendance WHERE activity_id = ?";
        List<Attendance> list = new ArrayList<>();
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(activityId));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Attendance a = new Attendance();
                a.setId(String.valueOf(rs.getInt("id")));
                a.setMemberId(String.valueOf(rs.getInt("member_id")));
                a.setAttendanceStatus(rs.getString("attendance_status"));
                list.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean existsByActivityAndMember(String activityId, String memberId) {
        String sql = "SELECT 1 FROM attendance WHERE activity_id = ? AND member_id = ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(activityId));
            ps.setInt(2, Integer.parseInt(memberId));
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}