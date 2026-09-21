package com.attendance.model;

import java.time.LocalDate;

/**
 * Model class representing a single attendance record.
 */
public class Attendance {

    private int id;
    private String studentName;
    private LocalDate date;
    private String status; // "Present" or "Absent"

    public Attendance() {
    }

    public Attendance(int id, String studentName, LocalDate date, String status) {
        this.id = id;
        this.studentName = studentName;
        this.date = date;
        this.status = status;
    }

    public Attendance(String studentName, LocalDate date, String status) {
        this.studentName = studentName;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", studentName='" + studentName + '\'' +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}