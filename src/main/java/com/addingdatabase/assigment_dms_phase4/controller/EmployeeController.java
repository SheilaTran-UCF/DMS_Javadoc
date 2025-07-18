package com.addingdatabase.assigment_dms_phase4.controller;

/**
 * Professor: Ashley Evans
 * Author: Minh Ngoc Tran
 * Course: 202530-CEN-3024C-31774
 * Date: July15 2025
 *
 * EmployeeController.java
 *
 * This class serves as the Spring MVC Controller for handling all web requests
 * related to employee management in the Data Management System (DMS) application.
 * It manages the flow of data between the user interface and the service layer.
 *
 * Key Features:
 * - Handles HTTP GET and POST requests for employee-related operations.
 * - Provides endpoints to list, add, update, delete, and report on employees.
 * - Uses Spring's @Autowired annotation to inject the EmployeeService dependency.
 * - Uses Model to pass data between the controller and HTML view templates.
 * - Supports form submission using @ModelAttribute and path variables using @PathVariable.
 * - Redirects users appropriately after data-modifying actions to maintain navigation flow.
 *
 * Mapped Endpoints:
 * - GET /employees       → Display a list of all employees.
 * - GET /add             → Show form to add a new employee.
 * - POST /save           → Save a new or updated employee.
 * - GET /update/{id}     → Show form to update an existing employee.
 * - GET /delete/{id}     → Delete an employee by ID.
 * - GET /tenure          → Display employee tenure report.
 * - GET /                → Display the home page.
 */

import com.addingdatabase.assigment_dms_phase4.model.Employee;
import com.addingdatabase.assigment_dms_phase4.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Displays a list of all employees.
     *
     * @param model the Spring Model used to pass data to the view
     * @return the name of the view that displays the employee list
     */
    @GetMapping("/employees")
    public String listEmployees(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employeeList";
    }

    /**
     * Displays the form to add a new employee.
     *
     * @param model the Spring Model used to pass data to the view
     * @return the name of the form view for creating an employee
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employeeForm";
    }

    /**
     * Saves a new or existing employee.
     *
     * @param employee the employee object to be saved
     * @param model the Spring Model used to pass error messages to the view
     * @return redirect to the employee list or return to form if validation fails
     */
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee, Model model) {
        if (employee.getHireDate() != null && employee.getHireDate().isAfter(LocalDate.now())) {
            model.addAttribute("dateError", "Hire date cannot be in the future.");
            return "employeeForm";
        }

        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    /**
     * Displays the form to update an existing employee.
     *
     * @param id the ID of the employee to update
     * @param model the Spring Model used to pass employee data to the view
     * @return the name of the form view with pre-filled employee data, or redirect if not found
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        System.out.println("Update request for employee id: " + id);
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return "redirect:/employees";
        }
        model.addAttribute("employee", employee);
        return "employeeForm";
    }

    /**
     * Deletes an employee by ID.
     *
     * @param id the ID of the employee to delete
     * @return redirect to the employee list page
     */
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }

    /**
     * Displays a report that groups employees by tenure.
     *
     * @param model the Spring Model used to pass tenure group data to the view
     * @return the name of the view that displays the tenure report
     */
    @GetMapping("/tenure")
    public String showTenureReport(Model model) {
        Map<String, List<Employee>> tenureGroups = employeeService.getEmployeesGroupedByTenure();
        model.addAttribute("tenureGroups", tenureGroups);
        return "tenureReport";
    }

    /**
     * Displays the home page of the application.
     *
     * @return the name of the home view
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }
}
