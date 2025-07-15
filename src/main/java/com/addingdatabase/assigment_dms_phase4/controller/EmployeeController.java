// Package declaration: defines which package this class belongs to
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


// Import Employee model class
import com.addingdatabase.assigment_dms_phase4.model.Employee;
// Import Employee service class
import com.addingdatabase.assigment_dms_phase4.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired; // Enables dependency injection
import org.springframework.stereotype.Controller;          // Marks this class as a Spring MVC controller
import org.springframework.ui.Model;                      // Holds data to pass to the view (HTML template)
import org.springframework.web.bind.annotation.*;         // Provides Spring MVC annotations like @GetMapping, @PostMapping, etc.

import java.util.List; // Import List collection for holding multiple Employee objects
import java.util.Map;  // Import Map collection for storing grouped data (tenure groups)

// Mark this class as a Spring MVC controller
@Controller
public class EmployeeController {

    // Inject EmployeeService dependency automatically via Spring
    @Autowired
    private EmployeeService employeeService;

    // ✅ Handle request to display a list of all employees
    @GetMapping("/employees")  // Maps GET requests to /employees URL
    public String listEmployees(Model model) {
        // Retrieve all employees via service layer
        List<Employee> employees = employeeService.getAllEmployees();
        // Add employees list to the model to make it available in the view
        model.addAttribute("employees", employees);
        // Return the view name (employeeList.html)
        return "employeeList";
    }

    // ✅ Show form for adding a new employee
    @GetMapping("/add")  // Maps GET requests to /add URL
    public String showAddForm(Model model) {
        // Add a new empty Employee object to the model (for form binding)
        model.addAttribute("employee", new Employee());
        // Return the view name (employeeForm.html)
        return "employeeForm";
    }

    // ✅ Save employee data (for both new and updated employees)
    @PostMapping("/save")  // Maps POST requests to /save URL
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        // Save or update employee using service layer
        employeeService.saveEmployee(employee);
        // Redirect user back to the employee list page
        return "redirect:/employees";
    }

    // ✅ Show form for updating an existing employee
    @GetMapping("/update/{id}")  // Maps GET requests to /update/{id} URL
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        // Log the id of the employee being updated (for debugging)
        System.out.println("Update request for employee id: " + id);
        // Retrieve employee by id
        Employee employee = employeeService.getEmployeeById(id);
        // If employee not found, redirect back to employee list
        if (employee == null) {
            return "redirect:/employees";
        }
        // Add employee to the model to pre-fill form fields for editing
        model.addAttribute("employee", employee);
        // Return the view name (employeeForm.html)
        return "employeeForm";
    }

    // ✅ Delete employee by id
    @GetMapping("/delete/{id}")  // Maps GET requests to /delete/{id} URL
    public String deleteEmployee(@PathVariable("id") Long id) {
        // Delete employee using service layer
        employeeService.deleteEmployee(id);
        // Redirect back to the employee list page
        return "redirect:/employees";
    }

    // ✅ Show report page grouping employees by tenure
    @GetMapping("/tenure")  // Maps GET requests to /tenure URL
    public String showTenureReport(Model model) {
        // Get employees grouped by tenure via service layer (returns Map<String, List<Employee>>)
        Map<String, List<Employee>> tenureGroups = employeeService.getEmployeesGroupedByTenure();
        // Add the grouped data to the model
        model.addAttribute("tenureGroups", tenureGroups);
        // Return the view name (tenureReport.html)
        return "tenureReport";
    }

    // ✅ Handle request for home page
    @GetMapping("/")  // Maps GET requests to the root URL
    public String home() {
        // Return the view name (index.html)
        return "index";
    }
}
