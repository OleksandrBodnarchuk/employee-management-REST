package com.obodnarchuk.employee;

import com.obodnarchuk.position.PositionResponseDTO;

import java.util.List;

public interface IEmployeeService {
    EmployeeResponseDTO saveEmployee(EmployeeRequestDTO employee);
     List<EmployeeResponseDTO> getAllEmployees();
    void deleteEmployeeById(long id);
    EmployeeResponseDTO getEmployeeById(long id);
    PositionResponseDTO getEmployeePosition(long id);
}
