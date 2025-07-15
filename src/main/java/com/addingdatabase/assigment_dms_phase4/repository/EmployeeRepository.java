// Package declaration: defines the package this class belongs to
package com.addingdatabase.assigment_dms_phase4.repository;

/**
 * Professor: Ashley Evans
 * Author: Minh Ngoc Tran
 * Course: 202530-CEN-3024C-31774
 * Date: July15 2025
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
 *
 * Note:
 * - This implementation uses plain JDBC for direct control over database operations,
 *   providing flexibility for managing SQLite’s special behaviors (such as AUTOINCREMENT sequence resets).
 *
 */

// Import the Employee entity class
import com.addingdatabase.assigment_dms_phase4.model.Employee;
// Import Spring's @Repository annotation to mark this class as a data access component
import org.springframework.stereotype.Repository;

// Import Java SQL libraries for database connection and result handling
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Mark this class as a repository bean for Spring’s dependency injection
@Repository
public class EmployeeRepository {

    // SQLite database connection URL (file path to your SQLite database)
    private final String url = "jdbc:sqlite:/Users/minhtran/Desktop/CEN-3024C/Module_10/Assigment_DMS_Phase4/dms.db";

    // ✅ Retrieve all employee records from the database
    public List<Employee> findAll() {
        // List to hold all Employee objects retrieved from the database
        List<Employee> employees = new ArrayList<>();
        System.out.println(">>> EmployeeRepository.findAll() called");

        // Try-with-resources block to automatically close database connections and statements
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM employees")) {

            // Loop through the result set and build Employee objects from each row
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
                // Add each Employee object to the list
                employees.add(e);
            }
            System.out.println(">>> Found " + employees.size() + " employees.");

        } catch (SQLException e) {
            // Print SQL exception details to console if an error occurs
            e.printStackTrace();
        }

        // Return the complete list of employees
        return employees;
    }

    // ✅ Save a new employee or update an existing employee
    public void save(Employee e) {
        // If id is null, prepare an INSERT query; otherwise, prepare an UPDATE query
        String sql = (e.getId() == null) ?
                "INSERT INTO employees(name, position, salary, hire_date, department, active) VALUES (?, ?, ?, ?, ?, ?)" :
                "UPDATE employees SET name=?, position=?, salary=?, hire_date=?, department=?, active=? WHERE id=?";

        System.out.println(">>> Save operation for: " + (e.getId() == null ? "INSERT" : "UPDATE"));
        System.out.println(">>> Employee: " + e);

        // Open a database connection and prepare the statement
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters for the prepared statement from the Employee object's fields
            pstmt.setString(1, e.getName());
            pstmt.setString(2, e.getPosition());
            pstmt.setDouble(3, e.getSalary());
            pstmt.setString(4, e.getHireDate().toString());
            pstmt.setString(5, e.getDepartment());
            pstmt.setBoolean(6, e.isActive());

            // If it's an update operation, set the id as the last parameter
            if (e.getId() != null) {
                pstmt.setLong(7, e.getId());
            }

            // Execute the INSERT or UPDATE statement
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("❌ SQL Error during save:");
            ex.printStackTrace();
        }
    }

    // ✅ Find a specific employee by ID
    public Employee findById(Long id) {
        // SQL query to select a single employee record by its ID
        String sql = "SELECT * FROM employees WHERE id = ?";

        // Open a database connection and prepare the statement
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the ID parameter in the prepared statement
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            // If a matching employee is found, build and return an Employee object
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

        // Return null if no employee is found with the given ID
        return null;
    }

    // ✅ Delete an employee by ID and reset sequence number
    public void deleteById(Long id) {
        // SQL query to delete a specific employee by ID
        String sql = "DELETE FROM employees WHERE id = ?";

        // Open a database connection and prepare the statement
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the ID parameter in the prepared statement
            pstmt.setLong(1, id);
            // Execute the DELETE statement
            pstmt.executeUpdate();

            // After deletion, reset the AUTOINCREMENT sequence value
            resetEmployeeSequence();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // ✅ Reset SQLite's AUTOINCREMENT sequence to avoid gaps after deletion
    public void resetEmployeeSequence() {
        // SQL query to reset the AUTOINCREMENT sequence to the highest existing ID
        String sql = "UPDATE sqlite_sequence SET seq = (SELECT MAX(id) FROM employees) WHERE name = 'employees'";

        // Open a database connection and execute the sequence update
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // Execute the update statement to reset the sequence
            stmt.executeUpdate(sql);
            System.out.println(">>> SQLite sequence reset for 'employees'");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
