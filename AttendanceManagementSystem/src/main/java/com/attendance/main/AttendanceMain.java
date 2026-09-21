package com.attendance.main;

import com.attendance.db.DBConnection;
import com.attendance.model.Attendance;
import com.attendance.service.AttendanceService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point of the Attendance Management System.
 * Provides a simple console menu to interact with the system.
 */
public class AttendanceMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AttendanceService service = new AttendanceService();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    markAttendance(scanner, service);
                    break;
                case "2":
                    viewAllAttendance(service);
                    break;
                case "3":
                    viewAttendanceForStudent(scanner, service);
                    break;
                case "4":
                    editAttendance(scanner, service);
                    break;
                case "5":
                    deleteAttendance(scanner, service);
                    break;
                case "6":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        DBConnection.closeConnection();
        scanner.close();
        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n===== Attendance Management System =====");
        System.out.println("1. Mark Attendance");
        System.out.println("2. View All Attendance");
        System.out.println("3. View Attendance by Student");
        System.out.println("4. Edit Attendance Status");
        System.out.println("5. Delete Attendance Record");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void markAttendance(Scanner scanner, AttendanceService service) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter status (Present/Absent): ");
        String status = scanner.nextLine();

        boolean success = service.markAttendance(name, LocalDate.now(), status);
        System.out.println(success ? "Attendance marked successfully." : "Failed to mark attendance.");
    }

    private static void viewAllAttendance(AttendanceService service) {
        List<Attendance> records = service.viewAllAttendance();
        if (records.isEmpty()) {
            System.out.println("No attendance records found.");
        } else {
            records.forEach(System.out::println);
        }
    }

    private static void viewAttendanceForStudent(Scanner scanner, AttendanceService service) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        List<Attendance> records = service.viewAttendanceForStudent(name);
        if (records.isEmpty()) {
            System.out.println("No records found for " + name);
        } else {
            records.forEach(System.out::println);
        }
    }

    private static void editAttendance(Scanner scanner, AttendanceService service) {
        System.out.print("Enter record ID to update: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter new status (Present/Absent): ");
        String status = scanner.nextLine();

        boolean success = service.editAttendanceStatus(id, status);
        System.out.println(success ? "Record updated." : "Update failed.");
    }

    private static void deleteAttendance(Scanner scanner, AttendanceService service) {
        System.out.print("Enter record ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        boolean success = service.removeAttendance(id);
        System.out.println(success ? "Record deleted." : "Delete failed.");
    }
}