package com.addingdatabase.assigment_dms_phase4.controller;


import com.addingdatabase.assigment_dms_phase4.model.Employee;
import com.addingdatabase.assigment_dms_phase4.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/employees")
    public String listEmployees(Model model) {
        model.addAttribute("employees", service.getAll());
        return "employees";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "addEmployee";
    }

    @PostMapping("/add")
    public String addEmployee(@Valid @ModelAttribute("employee") Employee employee,
                              BindingResult result) {
        if (result.hasErrors()) return "addEmployee";
        service.add(employee);
        return "redirect:/employees";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Employee employee = service.findById(id);
        if (employee == null) return "redirect:/employees";
        model.addAttribute("employee", employee);
        return "updateEmployee";
    }

    @PostMapping("/update")
    public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee,
                                 BindingResult result) {
        if (result.hasErrors()) return "updateEmployee";
        service.update(employee);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/employees";
    }

    @GetMapping("/tenure")
    public String tenureReport(Model model) {
        model.addAttribute("tenureGroups", service.getEmployeesGroupedByTenure());
        return "tenureReport";
    }
}
