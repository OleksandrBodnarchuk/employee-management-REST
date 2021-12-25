package com.obodnarchuk.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obodnarchuk.address.Address;
import com.obodnarchuk.address.AddressResponseDTO;
import com.obodnarchuk.address.AddressService;
import com.obodnarchuk.department.Department;
import com.obodnarchuk.department.DepartmentResponseDTO;
import com.obodnarchuk.department.DepartmentService;
import com.obodnarchuk.exceptions.RecordExistsException;
import com.obodnarchuk.exceptions.RecordNotFoundException;
import com.obodnarchuk.position.Position;
import com.obodnarchuk.position.PositionResponseDTO;
import com.obodnarchuk.position.PositionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService implements IEmployeeService {

    final ObjectMapper mapper;
    final EmployeeRepository repository;
    final PositionService positionService;
    final DepartmentService departmentService;
    final AddressService addressService;

    public EmployeeService(ObjectMapper mapper, EmployeeRepository repository, PositionService positionService, DepartmentService departmentService, AddressService addressService) {
        this.mapper = mapper;
        this.repository = repository;
        this.positionService = positionService;
        this.departmentService = departmentService;
        this.addressService = addressService;
    }

    @Override
    public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO requestDTO) {
        Employee employee = mapper.convertValue(requestDTO, Employee.class);
        createEmployeeEmail(employee);
        checkEmployeePosition(employee);
        checkEmployeeDepartment(employee);
        checkEmployeeAddress(employee);
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

    @Override
    public EmployeeResponseDTO updateEmployee(long id, EmployeeRequestDTO requestDTO) {
        Employee employee = findEmployeeOrThrow(id);
        checkDtoValuesAndMap(requestDTO, employee);
        createEmployeeEmail(employee);
        repository.save(employee);
        return mapToResponseDTO(employee);
    }

    private void checkDtoValuesAndMap(EmployeeRequestDTO requestDTO,Employee employee) {
        if (requestDTO.getName()!=null){
            employee.setName(requestDTO.getName());
        }
        if (requestDTO.getSurname()!=null){
            employee.setSurname(requestDTO.getSurname());
        }

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

    private void checkEmployeeDepartment(Employee employee) {
        Department departmentFromDb = departmentService.getDepartmentByName(employee.getDepartment().getName());
        // if department exists
        if (departmentFromDb!=null){
            employee.setDepartment(departmentFromDb);
        }
    }


    private void checkEmployeeAddress(Employee employee) {
         // check for employee address in address DB
        Optional<Address> employeeAddress = addressService.findAddress(employee.getAddress());
        employeeAddress.ifPresent(employee::setAddress);
    }

}
