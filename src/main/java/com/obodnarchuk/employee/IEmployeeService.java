package com.obodnarchuk.employee;

import java.util.List;

public interface IEmployeeService {
    EmployeeResponseDTO saveEmployee(EmployeeRequestDTO employee);
     List<EmployeeResponseDTO> getAllEmployees();
}
