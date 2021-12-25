package com.obodnarchuk.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obodnarchuk.address.Address;
import com.obodnarchuk.address.AddressService;
import com.obodnarchuk.department.Department;
import com.obodnarchuk.department.DepartmentService;
import com.obodnarchuk.exceptions.RecordNotFoundException;
import com.obodnarchuk.position.Position;
import com.obodnarchuk.position.PositionResponseDTO;
import com.obodnarchuk.position.PositionService;

import java.util.Optional;

public class EmployeeUtil {

    protected static PositionResponseDTO checkAndUpdatePosition(Position positionRequestDTO, Employee employeeFromDB,
                                                                PositionService positionService, ObjectMapper mapper) {
        PositionResponseDTO positionResponseDTO;
        Position positionByTitle = positionService.getByTitle(positionRequestDTO.getTitle());
        // check if position NOT exist in DB
        if (positionByTitle == null) {
            Position position = new Position(positionRequestDTO.getTitle());
            positionResponseDTO = positionService.savePosition(position);
            // assign new position to employee
            Position newPosition = positionService.getByTitle(positionResponseDTO.getTitle());
            employeeFromDB.setPosition(newPosition);
        } else {
            // set position to other available in DB position
            employeeFromDB.setPosition(positionByTitle);
            positionResponseDTO = mapper.convertValue(employeeFromDB.getPosition(), PositionResponseDTO.class);
        }
        return positionResponseDTO;
    }

    protected static void checkDtoValuesAndMap(EmployeeRequestDTO requestDTO, Employee employee) {
        if (requestDTO.getName() != null) {
            employee.setName(requestDTO.getName());
        }
        if (requestDTO.getSurname() != null) {
            employee.setSurname(requestDTO.getSurname());
        }

    }

    protected static EmployeeResponseDTO mapToResponseDTO(Employee employee,ObjectMapper mapper) {
        return mapper.convertValue(employee, EmployeeResponseDTO.class);
    }

    protected static Employee findEmployeeOrThrow(long id,EmployeeRepository repository) {
        return repository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }


    protected static void createEmployeeEmail(Employee employee) {
        employee.setEmail(employee.getName().trim().toLowerCase()
                + "." + employee.getSurname().trim().toLowerCase()
                + "@company.com");
    }

    protected static void checkEmployeePosition(Employee employee,PositionService positionService) {
        // if position exists
        if (employee.getPosition() != null) {
            Position positionFromDb = positionService.getByTitle(employee.getPosition().getTitle());
            employee.setPosition(positionFromDb);
        }
    }

    protected static void checkEmployeeDepartment(Employee employee, DepartmentService departmentService) {
        // if department exists
        if (employee.getDepartment() != null) {
            Department departmentFromDb = departmentService.getDepartmentByName(employee.getDepartment().getName());
            employee.setDepartment(departmentFromDb);
        }
    }


    protected static void checkEmployeeAddress(Employee employee, AddressService addressService) {
        // check for employee address in address DB
        Optional<Address> employeeAddress = addressService.findAddress(employee.getAddress());
        employeeAddress.ifPresent(employee::setAddress);
    }
}
