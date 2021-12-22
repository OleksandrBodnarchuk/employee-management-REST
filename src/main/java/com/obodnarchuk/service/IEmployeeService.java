package com.obodnarchuk.service;

import com.obodnarchuk.model.Employee;

import java.util.List;

public interface IEmployeeService {
    Employee saveEmployee(Employee employee);
     List<Employee> getAllEmployees();
}
