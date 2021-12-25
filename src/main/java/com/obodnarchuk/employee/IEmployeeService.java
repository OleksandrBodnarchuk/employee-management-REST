package com.obodnarchuk.employee;

import com.obodnarchuk.address.AddressResponseDTO;
import com.obodnarchuk.department.DepartmentResponseDTO;
import com.obodnarchuk.position.Position;
import com.obodnarchuk.position.PositionResponseDTO;

import java.util.List;

public interface IEmployeeService {
    EmployeeResponseDTO saveEmployee(EmployeeRequestDTO employee);

    List<EmployeeResponseDTO> getAllEmployees();

    void deleteEmployeeById(long id);

    EmployeeResponseDTO getEmployeeById(long id);

    PositionResponseDTO getEmployeePosition(long id);

    DepartmentResponseDTO getEmployeeDepartment(long id);

    AddressResponseDTO getEmployeeAddress(long id);

    EmployeeResponseDTO updateEmployee(long id, EmployeeRequestDTO requestDTO);

    PositionResponseDTO updatePosition(long id, Position positionRequestDTO);
}
