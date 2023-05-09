package com.example.GARA_API.repository;

import com.example.GARA_API.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Long>
{
    Employee findEmployeeById(Long id);
}
