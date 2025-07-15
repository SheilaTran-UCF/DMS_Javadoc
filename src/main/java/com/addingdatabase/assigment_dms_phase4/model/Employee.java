// Package declaration: defines the package where this class belongs
package com.addingdatabase.assigment_dms_phase4.model;

/**
 *
 *  Professor: Ashley Evans
 *  Author: Minh Ngoc Tran
 *  Course: 202530-CEN-3024C-31774
 *  Date: July15 2025
 *
 * Employee.java
 *
 * This class represents the Employee entity in the Data Management System (DMS) application.
 * It is a JPA entity mapped to the "employees" table in the database.
 *
 * Key Features:
 * - Maps employee-related data fields such as name, position, salary, hire date, department, and active status.
 * - Enforces data validation rules using Jakarta Bean Validation annotations (e.g., @NotBlank, @NotNull, @PositiveOrZero).
 * - Uses a custom attribute converter (LocalDateAttributeConverter) to handle persistence of LocalDate fields in the database.
 * - Provides multiple constructors for creating new employee records or working with existing ones.
 * - Includes standard getter and setter methods for accessing and modifying employee properties.
 *
 * Usage:
 * This entity is used by the application’s service and repository layers to perform
 * CRUD operations on employee records within the database.
 *
 */


// Import statements for required classes and annotations
import com.addingdatabase.assigment_dms_phase4.model.LocalDateAttributeConverter; // Custom converter for LocalDate <-> database

import jakarta.persistence.Convert;         // Annotation to specify a converter for a field
import jakarta.persistence.Column;         // Specifies details about database columns
import jakarta.persistence.Entity;         // Marks this class as a JPA entity (mapped to a database table)
import jakarta.persistence.GeneratedValue; // Specifies auto-generation of primary key values
import jakarta.persistence.GenerationType; // Strategy for primary key generation
import jakarta.persistence.Id;             // Marks the primary key field
import jakarta.persistence.Table;          // Specifies the table name in the database
import jakarta.validation.constraints.NotBlank;      // Validates that a String is not null and not empty (ignores whitespace)
import jakarta.validation.constraints.NotNull;       // Validates that a value is not null
import jakarta.validation.constraints.PositiveOrZero;// Validates that a number is zero or positive
import java.time.LocalDate;                // Represents a date without a time zone

// Marks this class as a persistent entity class mapped to a database table
@Entity
// Specifies the table name as "employees"
@Table(name = "employees")
public class Employee {

    // Primary key field with auto-increment strategy
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Employee name: cannot be null or blank
    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    // Employee position: cannot be null or blank
    @NotBlank(message = "Position is required")
    @Column(nullable = false)
    private String position;

    // Employee salary: must be zero or a positive number
    @PositiveOrZero(message = "Salary must be 0 or greater")
    @Column(nullable = false)
    private double salary;

    // Employee hire date: cannot be null
    @NotNull(message = "Hire date is required")
    @Column(name = "hire_date", nullable = false)
    // Use a custom converter to persist LocalDate as a database-compatible type
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate hireDate;

    // Employee department: cannot be null or blank
    @NotBlank(message = "Department is required")
    @Column(nullable = false)
    private String department;

    // Employee active status: cannot be null (since primitive boolean defaults to false)
    @Column(nullable = false)
    private boolean active;

    // No-argument constructor required by JPA
    public Employee() {}

    // Constructor for creating new employee instances (without id)
    public Employee(String name, String position, double salary, LocalDate hireDate, String department, boolean active) {
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
        this.department = department;
        this.active = active;
    }

    // Constructor for creating existing employee instances (with id)
    public Employee(Long id, String name, String position, double salary, LocalDate hireDate, String department, boolean active) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
        this.department = department;
        this.active = active;
    }

    // Getter for id
    public Long getId() { return id; }
    // Setter for id
    public void setId(Long id) { this.id = id; }

    // Getter for name
    public String getName() { return name; }
    // Setter for name
    public void setName(String name) { this.name = name; }

    // Getter for position
    public String getPosition() { return position; }
    // Setter for position
    public void setPosition(String position) { this.position = position; }

    // Getter for salary
    public double getSalary() { return salary; }
    // Setter for salary
    public void setSalary(double salary) { this.salary = salary; }

    // Getter for hireDate
    public LocalDate getHireDate() { return hireDate; }
    // Setter for hireDate
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    // Getter for department
    public String getDepartment() { return department; }
    // Setter for department
    public void setDepartment(String department) { this.department = department; }

    // Getter for active status
    public boolean isActive() { return active; }
    // Setter for active status
    public void setActive(boolean active) { this.active = active; }
}
