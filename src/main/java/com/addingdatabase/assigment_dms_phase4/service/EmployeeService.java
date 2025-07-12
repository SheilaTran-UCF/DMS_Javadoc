package com.addingdatabase.assigment_dms_phase4.service;

import com.addingdatabase.assigment_dms_phase4.model.Employee;
import com.addingdatabase.assigment_dms_phase4.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Employee findById(Long id) {
        return repository.findById(id);
    }

    public void add(Employee employee) {
        repository.save(employee);
    }

    public void update(Employee employee) {
        repository.save(employee);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Map<String, Long> getEmployeesGroupedByTenure() {
        LocalDate today = LocalDate.now();
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(e -> {
                    long years = today.getYear() - e.getHireDate().getYear();
                    if (years < 1) return "0–1 years";
                    else if (years <= 5) return "1–5 years";
                    else return "5+ years";
                }, Collectors.counting()));
    }
}
