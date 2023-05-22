package com.uyghur.java.employeeservice.controller;

import com.uyghur.java.employeeservice.exception.EmployeeNotFoundException;
import com.uyghur.java.employeeservice.model.Employee;
import com.uyghur.java.employeeservice.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    private final EmployeeRepository repository;

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees() {
        LOGGER.info("Employees have been found.");
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        LOGGER.info("Employee added: {}", employee);
        return new ResponseEntity<>(repository.save(employee), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        LOGGER.info("Employee has been found with id:{}", id);
        return ResponseEntity.ok(repository.findById(id).orElseThrow(
                ()-> new EmployeeNotFoundException("Patient with id:{%d} doesn't exist in database".formatted(id))));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartmentId(@PathVariable Long departmentId) {
        LOGGER.info("Employees have been found by department with id:{}", departmentId);
        return ResponseEntity.ok(repository.findByDepartmentId(departmentId));
    }
}
