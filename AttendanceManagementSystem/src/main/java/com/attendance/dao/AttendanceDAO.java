package com.attendance.dao;

import com.attendance.db.DBConnection;
import com.attendance.model.Attendance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Attendance records.
 * Handles all direct database operations (CRUD).
 */
public class AttendanceDAO {

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS attendance (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "student_name VARCHAR(100) NOT NULL, " +
            "date DATE NOT NULL, " +
            "status VARCHAR(10) NOT NULL)";

    public AttendanceDAO() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return;

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public boolean addAttendance(Attendance attendance) {
        String sql = "INSERT INTO attendance (student_name, date, status) VALUES (?, ?, ?)";
        Connection conn = DBConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, attendance.getStudentName());
            ps.setDate(2, java.sql.Date.valueOf(attendance.getDate()));
            ps.setString(3, attendance.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding attendance: " + e.getMessage());
            return false;
        }
    }

    public List<Attendance> getAllAttendance() {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM attendance ORDER BY date DESC";
        Connection conn = DBConnection.getConnection();
        if (conn == null) return list;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Attendance a = new Attendance(
                        rs.getInt("id"),
                        rs.getString("student_name"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("status")
                );
                list.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching attendance: " + e.getMessage());
        }
        return list;
    }

    public List<Attendance> getAttendanceByStudent(String studentName) {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE student_name = ? ORDER BY date DESC";
        Connection conn = DBConnection.getConnection();
        if (conn == null) return list;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Attendance a = new Attendance(
                            rs.getInt("id"),
                            rs.getString("student_name"),
                            rs.getDate("date").toLocalDate(),
                            rs.getString("status")
                    );
                    list.add(a);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching attendance for student: " + e.getMessage());
        }
        return list;
    }

    public boolean updateAttendance(int id, String newStatus) {
        String sql = "UPDATE attendance SET status = ? WHERE id = ?";
        Connection conn = DBConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating attendance: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteAttendance(int id) {
        String sql = "DELETE FROM attendance WHERE id = ?";
        Connection conn = DBConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting attendance: " + e.getMessage());
            return false;
        }
    }
}