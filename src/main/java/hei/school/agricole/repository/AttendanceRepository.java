package hei.school.agricole.repository;

import hei.school.agricole.config.DataSource;
import hei.school.agricole.entity.Attendance;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class AttendanceRepository {

    public void saveAll(String activityId, List<Attendance> attendances) {
        String sql = "INSERT INTO attendance (id, activity_id, member_id, attendance_status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Attendance a : attendances) {
                String id = UUID.randomUUID().toString();
                ps.setString(1, id);
                ps.setString(2, activityId);
                ps.setString(3, a.getMemberId());
                ps.setString(4, a.getAttendanceStatus());
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
            ps.setString(1, activityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Attendance a = new Attendance();
                a.setId(rs.getString("id"));
                a.setMemberId(rs.getString("member_id"));
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
            ps.setString(1, activityId);
            ps.setString(2, memberId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Attendance findByActivityIdAndMemberId(String activityId, String memberId) {
        String sql = "SELECT id, activity_id, member_id, attendance_status, recorded_at FROM attendance WHERE activity_id = ? AND member_id = ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, activityId);
            ps.setString(2, memberId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Attendance att = new Attendance();
                att.setId(rs.getString("id"));
                att.setActivityId(rs.getString("activity_id"));
                att.setMemberId(rs.getString("member_id"));
                att.setAttendanceStatus(rs.getString("attendance_status"));
                att.setRecordedAt(rs.getTimestamp("recorded_at").toLocalDateTime());
                return att;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}