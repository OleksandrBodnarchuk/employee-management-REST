package com.obodnarchuk.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obodnarchuk.address.*;
import com.obodnarchuk.department.*;
import com.obodnarchuk.exceptions.CSVFileError;
import com.obodnarchuk.exceptions.RecordExistsException;
import com.obodnarchuk.position.Position;
import com.obodnarchuk.position.PositionResponseDTO;
import com.obodnarchuk.position.PositionService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        if (employee.getDepartment()==null){
            employee.setDepartment(DepartmentUtil.mapToEntity(requestDTO.getDepartment(),mapper));
        }
        if (employee.getPosition()==null){
            employee.setPosition(requestDTO.getPosition());
        }
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

    @Override
    public List<EmployeeResponseDTO> saveFromCSV(MultipartFile file) {
        List<EmployeeResponseDTO>results = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(file.getInputStream(), StandardCharsets.ISO_8859_1));
            while (reader.ready()) {
                String line = reader.readLine();
                String[] split = line.split(",");
                if (split.length>0){
                    String name = split[0];
                    String surname = split[1];
//                String email = split[2]; email creates on its own
                    String phone = split[3];
                    String salary = split[4];
                    String stringDate = split[5];
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    LocalDate startDate = LocalDate.parse(stringDate, formatter);
                    Position position = new Position(split[6]);
                    String empAddressStreet = split[7];
                    String empAddressHouse = split[8];
                    String empAddressCity = split[9];
                    String empAddressZipCode = split[10];
                    AddressDTO empAddress = new AddressDTO(empAddressStreet, empAddressHouse, empAddressZipCode, empAddressCity);
                    String deptName = split[11];
                    String deptAddressStreet = split[12];
                    String deptAddressHouse = split[13];
                    String deptAddressCity = split[14];
                    String deptAddressZipCode = split[15];
                    AddressDTO deptAddress = new AddressDTO(deptAddressStreet,deptAddressHouse,deptAddressZipCode,deptAddressCity);
                    DepartmentRequestDTO department = new DepartmentRequestDTO(deptName,deptAddress);
                    EmployeeRequestDTO requestDTO = new EmployeeRequestDTO(
                            name, surname, Long.parseLong(phone),
                            startDate, Integer.parseInt(salary), empAddress,department,position);
                    EmployeeResponseDTO responseDTO = saveEmployee(requestDTO);
                    results.add(responseDTO);
                }
            }
        } catch (IOException e) {
            throw new CSVFileError("import");
        }
        return results;
    }

    public void writeEmployeesToCsv(Writer writer) {

        List<Employee> employees = repository.findAll();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (Employee employee : employees) {
                csvPrinter.printRecord(
//                        employee.getId(),
                        employee.getName(),
                        employee.getSurname(),
                        employee.getEmail(),
                        employee.getPhone(),
                        employee.getSalary(),
                        employee.getStartDate(),
//                        employee.getPosition().getId(),
                        employee.getPosition().getTitle(),
//                        employee.getAddress().getId(),
                        employee.getAddress().getStreet(),
                        employee.getAddress().getHouseNr(),
                        employee.getAddress().getCity(),
                        employee.getAddress().getZipCode(),
//                        employee.getDepartment().getId(),
                        employee.getDepartment().getName(),
//                        employee.getDepartment().getAddress().getId(),
                        employee.getDepartment().getAddress().getStreet(),
                        employee.getDepartment().getAddress().getHouseNr(),
                        employee.getDepartment().getAddress().getCity(),
                        employee.getDepartment().getAddress().getZipCode()
                );
            }
        } catch (IOException e) {
            throw new CSVFileError("export");
        }
    }
}
