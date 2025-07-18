package com.addingdatabase.assigment_dms_phase4.repository;

/**
 * Professor: Ashley Evans
 * Author: Minh Ngoc Tran
 * Course: 202530-CEN-3024C-31774
 * Date: July 15, 2025
 *
 * EmployeeRepository.java
 *
 * This class serves as the data access layer for the Data Management System (DMS) application.
 * It uses JDBC (Java Database Connectivity) to directly interact with an SQLite database,
 * performing CRUD (Create, Read, Update, Delete) operations on employee records.
 *
 * Key Features:
 * - Connects to a local SQLite database using a JDBC URL.
 * - Retrieves all employee records with the findAll() method.
 * - Saves new employees or updates existing employee records with the save() method.
 * - Retrieves a single employee by ID using findById().
 * - Deletes an employee by ID with deleteById() and automatically resets the AUTOINCREMENT sequence.
 * - Resets the SQLite sequence number to the current highest employee ID to avoid ID gaps.
 *
 * Usage:
 * This class is annotated with @Repository, allowing Spring to detect and manage it as a data
 * access component. It is typically called from the service layer to handle database operations.
 */

import com.addingdatabase.assigment_dms_phase4.model.Employee;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    /**
     * Retrieves the SQLite database URL from system properties.
     *
     * @return the SQLite connection URL
     * @throws IllegalStateException if the URL is not properly configured
     */
    private String getUrl() {
        String url = System.getProperty("spring.datasource.url");
        if (url == null || url.isBlank()) {
            throw new IllegalStateException("❌ Database URL is not set. Please provide a valid SQLite path.");
        }
        return url;
    }

    /**
     * Retrieves all employee records from the database.
     *
     * @return a list of all employees
     */
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        System.out.println(">>> EmployeeRepository.findAll() called");

        try (Connection conn = DriverManager.getConnection(getUrl());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM employees")) {

            while (rs.next()) {
                Employee e = new Employee(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getDouble("salary"),
                        LocalDate.parse(rs.getString("hire_date")),
                        rs.getString("department"),
                        rs.getBoolean("active")
                );
                employees.add(e);
            }
            System.out.println(">>> Found " + employees.size() + " employees.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    /**
     * Saves a new employee to the database or updates an existing one.
     *
     * @param e the employee object to save or update
     */
    public void save(Employee e) {
        String sql = (e.getId() == null) ?
                "INSERT INTO employees(name, position, salary, hire_date, department, active) VALUES (?, ?, ?, ?, ?, ?)" :
                "UPDATE employees SET name=?, position=?, salary=?, hire_date=?, department=?, active=? WHERE id=?";

        System.out.println(">>> Save operation for: " + (e.getId() == null ? "INSERT" : "UPDATE"));
        System.out.println(">>> Employee: " + e);

        try (Connection conn = DriverManager.getConnection(getUrl());
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, e.getName());
            pstmt.setString(2, e.getPosition());
            pstmt.setDouble(3, e.getSalary());
            pstmt.setString(4, e.getHireDate().toString());
            pstmt.setString(5, e.getDepartment());
            pstmt.setBoolean(6, e.isActive());

            if (e.getId() != null) {
                pstmt.setLong(7, e.getId());
            }

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("❌ SQL Error during save:");
            ex.printStackTrace();
        }
    }

    /**
     * Finds a single employee by their unique ID.
     *
     * @param id the ID of the employee to retrieve
     * @return the employee if found, or null if not found
     */
    public Employee findById(Long id) {
        String sql = "SELECT * FROM employees WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(getUrl());
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Employee(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getDouble("salary"),
                        LocalDate.parse(rs.getString("hire_date")),
                        rs.getString("department"),
                        rs.getBoolean("active")
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Deletes an employee by their ID and resets the SQLite AUTOINCREMENT sequence.
     *
     * @param id the ID of the employee to delete
     */
    public void deleteById(Long id) {
        String sql = "DELETE FROM employees WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(getUrl());
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();

            resetEmployeeSequence();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Resets the SQLite sequence for the employees table based on the current max ID.
     * This helps maintain continuity for AUTOINCREMENT values.
     */
    public void resetEmployeeSequence() {
        String sql = "UPDATE sqlite_sequence SET seq = (SELECT MAX(id) FROM employees) WHERE name = 'employees'";

        try (Connection conn = DriverManager.getConnection(getUrl());
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println(">>> SQLite sequence reset for 'employees'");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
