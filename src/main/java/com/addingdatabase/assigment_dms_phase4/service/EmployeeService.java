package com.addingdatabase.assigment_dms_phase4.service;

/**
 * Professor: Ashley Evans
 * Author: Minh Ngoc Tran
 * Course: 202530-CEN-3024C-31774
 * Date: July 15, 2025
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
 */

import com.addingdatabase.assigment_dms_phase4.model.Employee;
import com.addingdatabase.assigment_dms_phase4.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class EmployeeService {

    /**
     * Repository for employee data access.
     */
    private final EmployeeRepository employeeRepository;

    /**
     * Constructor that uses constructor-based dependency injection to inject the EmployeeRepository.
     *
     * @param employeeRepository The repository interface for accessing employee data.
     */
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Retrieves all employees from the database.
     *
     * @return A list of all employees.
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Saves a new employee or updates an existing employee.
     * Throws an exception if the hire date is in the future.
     *
     * @param employee The employee to save or update.
     * @throws IllegalArgumentException If the hire date is in the future.
     */
    public void saveEmployee(Employee employee) {
        if (employee.getHireDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Hire date cannot be in the future.");
        }
        employeeRepository.save(employee);
    }

    /**
     * Retrieves an employee by their unique ID.
     *
     * @param id The unique identifier of the employee.
     * @return The Employee object if found; otherwise, null.
     */
    public Employee getEmployeeById(Long id) {
        Employee e = employeeRepository.findById(id);
        System.out.println(">>> getEmployeeById(" + id + ") returned: " + e);
        return e;
    }

    /**
     * Deletes an employee from the database based on their ID.
     *
     * @param id The unique identifier of the employee to delete.
     */
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    /**
     * Groups employees into tenure-based categories:
     * - "1–5 years"
     * - "5+ years"
     *
     * @return A map where keys are tenure ranges and values are lists of employees in each range.
     */
    public Map<String, List<Employee>> getEmployeesGroupedByTenure() {
        List<Employee> allEmployees = employeeRepository.findAll();
        Map<String, List<Employee>> groups = new LinkedHashMap<>();
        groups.put("1–5 years", new ArrayList<>());
        groups.put("5+ years", new ArrayList<>());

        LocalDate today = LocalDate.now();

        for (Employee emp : allEmployees) {
            LocalDate hireDate = emp.getHireDate();

            if (hireDate != null) {
                long years = ChronoUnit.YEARS.between(hireDate, today);

                if (years >= 1 && years <= 5) {
                    groups.get("1–5 years").add(emp);
                } else if (years > 5) {
                    groups.get("5+ years").add(emp);
                }
            }
        }

        return groups;
    }
}
