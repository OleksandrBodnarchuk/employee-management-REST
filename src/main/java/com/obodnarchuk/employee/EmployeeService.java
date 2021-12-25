package com.obodnarchuk.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obodnarchuk.address.AddressResponseDTO;
import com.obodnarchuk.department.DepartmentResponseDTO;
import com.obodnarchuk.exceptions.RecordExistsException;
import com.obodnarchuk.exceptions.RecordNotFoundException;
import com.obodnarchuk.position.Position;
import com.obodnarchuk.position.PositionResponseDTO;
import com.obodnarchuk.position.PositionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService implements IEmployeeService {

    final ObjectMapper mapper;
    final EmployeeRepository repository;
    final PositionService positionService;

    public EmployeeService(ObjectMapper mapper, EmployeeRepository repository, PositionService positionService) {
        this.mapper = mapper;
        this.repository = repository;
        this.positionService = positionService;
    }

    @Override
    public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO requestDTO) {
        Employee employee = mapper.convertValue(requestDTO, Employee.class);
        createEmployeeEmail(employee);
        checkEmployeePosition(employee);
        try {
            repository.save(employee);
        } catch (Exception e) {
            throw new RecordExistsException(employee.getEmail());
        }
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

    @Override
    public DepartmentResponseDTO getEmployeeDepartment(long id) {
        Employee employee = findEmployeeOrThrow(id);
        return mapper.convertValue(employee.getDepartment(), DepartmentResponseDTO.class);
    }

    @Override
    public AddressResponseDTO getEmployeeAddress(long id) {
        Employee employee = findEmployeeOrThrow(id);
        return mapper.convertValue(employee.getAddress(), AddressResponseDTO.class);
    }

    private EmployeeResponseDTO mapToResponseDTO(Employee employee) {
        return mapper.convertValue(employee, EmployeeResponseDTO.class);
    }

    private Employee findEmployeeOrThrow(long id) {
        return repository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }


    private void createEmployeeEmail(Employee employee) {
        employee.setEmail(employee.getName().trim().toLowerCase()
                + "." + employee.getSurname().trim().toLowerCase()
                + "@company.com");
    }

    private void checkEmployeePosition(Employee employee) {
        Position positionFromDb = positionService.getPositionByTitle(employee.getPosition().getTitle());
        // if position exists
        if (positionFromDb!=null){
            employee.setPosition(positionFromDb);
        }
    }

}
