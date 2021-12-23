package com.obodnarchuk.employee;

import java.util.List;

public interface IEmployeeService {
    Employee saveEmployee(Employee employee);
     List<Employee> getAllEmployees();
}
