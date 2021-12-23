package com.obodnarchuk.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }


}
