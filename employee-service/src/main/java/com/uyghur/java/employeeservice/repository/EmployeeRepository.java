package com.uyghur.java.employeeservice.repository;

import com.uyghur.java.employeeservice.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
   List<Employee> findByDepartmentId(Long id);
}
