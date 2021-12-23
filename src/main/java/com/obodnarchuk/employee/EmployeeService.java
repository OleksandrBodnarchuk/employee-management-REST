package com.obodnarchuk.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService implements IEmployeeService {

    final ObjectMapper mapper;
    final EmployeeRepository repository;

    public EmployeeService(ObjectMapper mapper, EmployeeRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO requestDTO) {
        Employee employee = mapper.convertValue(requestDTO, Employee.class);
        repository.save(employee);
        return mapper.convertValue(employee, EmployeeResponseDTO.class);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employeesFromDB = repository.findAll();
        return employeesFromDB.stream().map(e -> mapper.convertValue(e, EmployeeResponseDTO.class)).collect(Collectors.toList());
    }


}
