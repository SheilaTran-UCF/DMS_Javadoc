// Package declaration: defines the package this class belongs to
package com.addingdatabase.assigment_dms_phase4.service;

/**
 * Professor: Ashley Evans
 * Author: Minh Ngoc Tran
 * Course: 202530-CEN-3024C-31774
 * Date: July15 2025
 *
 * EmployeeService.java
 *
 * This class provides the service layer for managing employee data within the
 * Data Management System (DMS) application. It acts as an intermediary between
 * the controller layer and the repository (data access) layer.
 *
 * Key Responsibilities:
 * - Retrieve all employee records.
 * - Save new or update existing employee records.
 * - Find employees by their unique ID.
 * - Delete employees by ID.
 * - Generate tenure-based grouping reports categorizing employees by years of service.
 *
 * Usage:
 * The service uses dependency injection to access the EmployeeRepository for
 * database operations and contains business logic such as calculating tenure groups.
 *
 */


// Import Employee entity class
import com.addingdatabase.assigment_dms_phase4.model.Employee;
// Import EmployeeRepository for database operations
import com.addingdatabase.assigment_dms_phase4.repository.EmployeeRepository;
// Import Spring annotation to mark this class as a service
import org.springframework.stereotype.Service;

// Import annotation for dependency injection
import org.springframework.beans.factory.annotation.Autowired;

// Imports for date/time handling and collections
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

// Mark this class as a Spring service component
@Service
public class EmployeeService {

    // Autowire EmployeeRepository bean for dependency injection (field injection)
    @Autowired
    private EmployeeRepository repository;

    // Constructor-based injection of EmployeeRepository
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // ✅ Retrieve all employees from the database by delegating to repository
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // ✅ Save a new employee or update an existing employee
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    // ✅ Find a specific employee by ID, with a debug print statement
    public Employee getEmployeeById(Long id) {
        Employee e = repository.findById(id);
        System.out.println(">>> getEmployeeById(" + id + ") returned: " + e);
        return e;
    }

    // ✅ Delete employee by ID by delegating to repository
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    // ✅ Generate a report grouping employees by tenure at the company
    public Map<String, List<Employee>> getEmployeesGroupedByTenure() {
        // Get list of all employees
        List<Employee> allEmployees = employeeRepository.findAll();

        // Initialize map to hold two groups: "1–5 years" and "5+ years"
        Map<String, List<Employee>> groups = new LinkedHashMap<>();
        groups.put("1–5 years", new ArrayList<>());
        groups.put("5+ years", new ArrayList<>());

        // Get today's date for tenure calculation
        LocalDate today = LocalDate.now();

        // Loop over all employees to categorize them by years worked
        for (Employee emp : allEmployees) {
            LocalDate hireDate = emp.getHireDate();

            // Calculate years between hire date and today, if hire date is not null
            if (hireDate != null) {
                long years = ChronoUnit.YEARS.between(hireDate, today);

                // Add employee to "1–5 years" group if tenure is between 1 and 5 years inclusive
                if (years >= 1 && years <= 5) {
                    groups.get("1–5 years").add(emp);
                }
                // Add employee to "5+ years" group if tenure is more than 5 years
                else if (years > 5) {
                    groups.get("5+ years").add(emp);
                }
            }
        }

        // Return the grouped employees map for use in reports/views
        return groups;
    }
}
