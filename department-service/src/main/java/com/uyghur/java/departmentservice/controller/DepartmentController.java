package com.uyghur.java.departmentservice.controller;

import com.uyghur.java.departmentservice.client.EmployeeClient;
import com.uyghur.java.departmentservice.exception.DepartmentNotFoundException;
import com.uyghur.java.departmentservice.model.Department;
import com.uyghur.java.departmentservice.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    private final EmployeeClient employeeClient;
    public DepartmentController(EmployeeClient employeeClient, DepartmentRepository repository) {
        this.employeeClient = employeeClient;
        this.repository = repository;
    }

    private final DepartmentRepository repository;

    @GetMapping
    public ResponseEntity<List<Department>> getDepartments() {
        LOGGER.info("Departments have been found.");
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
        LOGGER.info("Department added: {}", department);
        return new ResponseEntity<>(repository.save(department), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        LOGGER.info("Department has been found with id:{}", id);
        return ResponseEntity.ok(repository.findById(id).orElseThrow(
                ()-> new DepartmentNotFoundException("Patient with id:{%d} doesn't exist in database".formatted(id))));
    }

    @GetMapping("/with-employees")
    public ResponseEntity<List<Department>> getDepartmentsAndEmployees() {
        List<Department> departmentList = repository.findAll();
        departmentList.forEach(department -> {
            department.setEmployees(employeeClient.getEmployeesByDepartmentId(department.getId()));
        });
        LOGGER.info("Departments and Employees have been found.");
        return ResponseEntity.ok(departmentList);
    }
}
