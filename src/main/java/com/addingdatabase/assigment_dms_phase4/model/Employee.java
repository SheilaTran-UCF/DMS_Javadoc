package com.addingdatabase.assigment_dms_phase4.model;

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
    private String name;

    @NotBlank(message = "Position is required")
    private String position;

    @PositiveOrZero(message = "Salary must be 0 or greater")
    private double salary;

    @NotNull(message = "Hire date is required")
    @Column(name = "hire_date")
    private LocalDate hireDate;

    @NotBlank(message = "Department is required")
    private String department;

    private boolean active;

    // No-arg constructor for JPA
    public Employee() {}

    // Constructor without id (for new employees)
    public Employee(String name, String position, double salary, LocalDate hireDate, String department, boolean active) {
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
        this.department = department;
        this.active = active;
    }

    // Constructor with id (for existing employees)
    public Employee(Long id, String name, String position, double salary, LocalDate hireDate, String department, boolean active) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
        this.department = department;
        this.active = active;
    }

    // Getters and setters for all fields

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}


