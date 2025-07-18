package com.addingdatabase.assigment_dms_phase4.model;

/**
 * Professor: Ashley Evans
 * Author: Minh Ngoc Tran
 * Course: 202530-CEN-3024C-31774
 * Date: July 15, 2025
 *
 * Employee.java
 *
 * This class represents the Employee entity in the Data Management System (DMS) application.
 * It is a JPA entity mapped to the "employees" table in the database.
 *
 * Key Features:
 * - Maps employee-related data fields such as name, position, salary, hire date, department, and active status.
 * - Enforces data validation rules using Jakarta Bean Validation annotations.
 * - Uses a custom attribute converter (LocalDateAttributeConverter) for persisting LocalDate fields.
 * - Provides multiple constructors and accessor methods.
 * - Supports CRUD operations through service and repository layers.
 */

import com.addingdatabase.assigment_dms_phase4.model.LocalDateAttributeConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Position is required")
    @Column(nullable = false)
    private String position;

    @PositiveOrZero(message = "Salary must be 0 or greater")
    @Column(nullable = false)
    private double salary;

    @NotNull(message = "Hire date is required")
    @Column(name = "hire_date", nullable = false)
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate hireDate;

    @NotBlank(message = "Department is required")
    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private boolean active;

    /**
     * Default constructor required by JPA.
     */
    public Employee() {}

    /**
     * Constructs a new Employee without an ID (for creating new entries).
     *
     * @param name       the employee's name
     * @param position   the employee's job position
     * @param salary     the employee's salary
     * @param hireDate   the date the employee was hired
     * @param department the department the employee belongs to
     * @param active     whether the employee is currently active
     */
    public Employee(String name, String position, double salary, LocalDate hireDate, String department, boolean active) {
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
        this.department = department;
        this.active = active;
    }

    /**
     * Constructs an Employee with all fields (used when editing existing records).
     *
     * @param id         the employee's unique ID
     * @param name       the employee's name
     * @param position   the employee's job position
     * @param salary     the employee's salary
     * @param hireDate   the date the employee was hired
     * @param department the department the employee belongs to
     * @param active     whether the employee is currently active
     */
    public Employee(Long id, String name, String position, double salary, LocalDate hireDate, String department, boolean active) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
        this.department = department;
        this.active = active;
    }

    /**
     * Gets the employee ID.
     *
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the employee ID.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the employee name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the employee name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the employee's position.
     *
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the employee's position.
     *
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Gets the employee's salary.
     *
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the employee's salary.
     *
     * @param salary the salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Gets the hire date.
     *
     * @return the hire date
     */
    public LocalDate getHireDate() {
        return hireDate;
    }

    /**
     * Sets the hire date.
     *
     * @param hireDate the hire date to set
     */
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    /**
     * Gets the employee's department.
     *
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the employee's department.
     *
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Checks if the employee is active.
     *
     * @return true if active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the employee's active status.
     *
     * @param active true to mark as active, false otherwise
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
