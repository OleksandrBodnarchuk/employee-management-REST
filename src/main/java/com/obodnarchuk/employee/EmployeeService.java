package com.obodnarchuk.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obodnarchuk.exceptions.RecordNotFoundException;
import com.obodnarchuk.position.PositionResponseDTO;
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
        return mapToResponseDTO(employee);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employeesFromDB = repository.findAll();
        return employeesFromDB.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteEmployeeById(long id) {
        Employee employee = findEmployeeOrThrow(id);
        repository.delete(employee);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(long id) {
        Employee employee = findEmployeeOrThrow(id);
        return mapToResponseDTO(employee);
    }

    @Override
    public PositionResponseDTO getEmployeePosition(long id) {
        Employee employee = findEmployeeOrThrow(id);
        return mapper.convertValue(employee.getPosition(), PositionResponseDTO.class);
    }

    private EmployeeResponseDTO mapToResponseDTO(Employee employee) {
        return mapper.convertValue(employee, EmployeeResponseDTO.class);
    }

    private Employee findEmployeeOrThrow(long id) {
        return repository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }


}
