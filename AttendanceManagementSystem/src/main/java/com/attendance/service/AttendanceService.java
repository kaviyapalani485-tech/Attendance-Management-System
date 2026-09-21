package com.attendance.service;

import com.attendance.dao.AttendanceDAO;
import com.attendance.model.Attendance;

import java.time.LocalDate;
import java.util.List;

/**
 * Business logic layer. Sits between the Main (UI) class and the DAO,
 * performing validation before delegating to the database layer.
 */
public class AttendanceService {

    private final AttendanceDAO attendanceDAO;

    public AttendanceService() {
        this.attendanceDAO = new AttendanceDAO();
    }

    public boolean markAttendance(String studentName, LocalDate date, String status) {
        if (studentName == null || studentName.trim().isEmpty()) {
            System.out.println("Student name cannot be empty.");
            return false;
        }
        if (!status.equalsIgnoreCase("Present") && !status.equalsIgnoreCase("Absent")) {
            System.out.println("Status must be 'Present' or 'Absent'.");
            return false;
        }

        Attendance attendance = new Attendance(studentName.trim(), date, status);
        return attendanceDAO.addAttendance(attendance);
    }

    public List<Attendance> viewAllAttendance() {
        return attendanceDAO.getAllAttendance();
    }

    public List<Attendance> viewAttendanceForStudent(String studentName) {
        return attendanceDAO.getAttendanceByStudent(studentName);
    }

    public boolean editAttendanceStatus(int id, String newStatus) {
        if (!newStatus.equalsIgnoreCase("Present") && !newStatus.equalsIgnoreCase("Absent")) {
            System.out.println("Status must be 'Present' or 'Absent'.");
            return false;
        }
        return attendanceDAO.updateAttendance(id, newStatus);
    }

    public boolean removeAttendance(int id) {
        return attendanceDAO.deleteAttendance(id);
    }
}