package com.addingdatabase.assigment_dms_phase4.repository;

import com.addingdatabase.assigment_dms_phase4.model.Employee;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private final String url = "jdbc:sqlite:DMS_db/dms.db";

    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM employees")) {

            while (rs.next()) {
                Employee e = new Employee(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getDouble("salary"),
                        rs.getDate("hire_date").toLocalDate(),
                        rs.getString("department"),
                        rs.getBoolean("active")
                );
                employees.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public void save(Employee e) {
        String sql = (e.getId() == null) ?
                "INSERT INTO employees(name, position, salary, hire_date, department, active) VALUES (?, ?, ?, ?, ?, ?)" :
                "UPDATE employees SET name=?, position=?, salary=?, hire_date=?, department=?, active=? WHERE id=?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, e.getName());
            pstmt.setString(2, e.getPosition());
            pstmt.setDouble(3, e.getSalary());
            pstmt.setString(4, e.getHireDate().toString());
            pstmt.setString(5, e.getDepartment());
            pstmt.setBoolean(6, e.isActive());

            if (e.getId() != null) pstmt.setLong(7, e.getId());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Employee findById(Long id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Employee(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getDouble("salary"),
                        rs.getDate("hire_date").toLocalDate(),
                        rs.getString("department"),
                        rs.getBoolean("active")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}