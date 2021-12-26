package com.obodnarchuk.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obodnarchuk.address.*;
import com.obodnarchuk.department.*;
import com.obodnarchuk.exceptions.RecordExistsException;
import com.obodnarchuk.position.Position;
import com.obodnarchuk.position.PositionResponseDTO;
import com.obodnarchuk.position.PositionService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

import static com.obodnarchuk.employee.EmployeeUtil.*;

@Service
public class EmployeeService implements IEmployeeService {

    final ObjectMapper mapper;
    final EmployeeRepository repository;
    final PositionService positionService;
    final DepartmentService departmentService;
    final AddressService addressService;
    @PersistenceContext
    private EntityManager entityManager;

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
        checkEmployeePosition(employee, positionService);
        checkEmployeeDepartment(employee, departmentService);
        checkEmployeeAddress(employee, addressService);
        try {
            repository.save(employee);
        } catch (Exception e) {
            throw new RecordExistsException(employee.getEmail());
        }
        return mapToResponseDTO(employee, mapper);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employeesFromDB = repository.findAll();
        return employeesFromDB.stream().map(e -> mapToResponseDTO(e, mapper)).collect(Collectors.toList());
    }

    @Override
    public void deleteEmployeeById(long id) {
        Employee employee = findEmployeeOrThrow(id, repository);
        repository.delete(employee);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(long id) {
        Employee employee = findEmployeeOrThrow(id, repository);
        return mapToResponseDTO(employee, mapper);
    }

    @Override
    public PositionResponseDTO getEmployeePosition(long id) {
        Employee employee = findEmployeeOrThrow(id, repository);
        return mapper.convertValue(employee.getPosition(), PositionResponseDTO.class);
    }

    @Override
    public DepartmentResponseDTO getEmployeeDepartment(long id) {
        Employee employee = findEmployeeOrThrow(id, repository);
        return mapper.convertValue(employee.getDepartment(), DepartmentResponseDTO.class);
    }

    @Override
    public AddressResponseDTO getEmployeeAddress(long id) {
        Employee employee = findEmployeeOrThrow(id, repository);
        return mapper.convertValue(employee.getAddress(), AddressResponseDTO.class);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(long id, EmployeeRequestDTO requestDTO) {
        Employee employee = findEmployeeOrThrow(id, repository);
        checkDtoValuesAndMap(requestDTO, employee);
        createEmployeeEmail(employee);
        repository.save(employee);
        return mapToResponseDTO(employee, mapper);
    }

    @Override
    public PositionResponseDTO updatePosition(long id, Position positionRequestDTO) {
        Employee employeeFromDB = findEmployeeOrThrow(id, repository);
        // check for duplicates in DB
        PositionResponseDTO positionResponseDTO =
                checkAndUpdatePosition(positionRequestDTO, employeeFromDB, positionService, mapper);
        updateEmployee(id, mapper.convertValue(employeeFromDB, EmployeeRequestDTO.class));
        return positionResponseDTO;
    }

    @Override
    public DepartmentResponseDTO updateDepartment(long employeeId, DepartmentRequestDTO departmentRequestDTO) {
        // find department in DB - has ID
        Department departmentFromDB = departmentService.getDepartmentByName(departmentRequestDTO.getName());
        if (departmentFromDB == null) {
            // save new department into DB if not exists
            DepartmentResponseDTO departmentResponseDTO = departmentService.saveDepartment(departmentRequestDTO);
            departmentFromDB = mapper.convertValue(departmentResponseDTO, Department.class);
        }
        repository.updateEmployeeDepartment(employeeId, departmentFromDB);

        return DepartmentUtil.mapToResponseDTO(departmentFromDB, mapper);
    }

    @Override
    public AddressResponseDTO updateAddress(long employeeId, AddressRequestDTO addressRequestDTO) {
        Address newAddress = AddressUtil.mapRequestToAddress(addressRequestDTO, mapper);
        Optional<Address> addressFromDB = addressService.findAddress(newAddress);
        if (addressFromDB.isEmpty()) {
            AddressResponseDTO addressResponseDTO = addressService.saveAddress(addressRequestDTO);
            addressFromDB = Optional.of(mapper.convertValue(addressResponseDTO, Address.class));
        }
        repository.updateEmployeeAddress(employeeId, addressFromDB.get());
        return AddressUtil.mapToResponse(addressFromDB.get(), mapper);
    }

    @Override
    public Set<EmployeeSalaryDbDTO> getAverageSalary() {
        List<EmployeeSalaryDbDTO> fromDB = repository.getPositionBySeniority();
        return returnAverageSalary(fromDB, repository);
    }
}
