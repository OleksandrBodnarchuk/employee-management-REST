package com.obodnarchuk.service;

import com.obodnarchuk.model.Employee;
import com.obodnarchuk.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements IEmployeeService{

    final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return repository.save(employee);
    }
}
