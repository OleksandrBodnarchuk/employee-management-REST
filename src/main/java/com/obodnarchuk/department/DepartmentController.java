package com.obodnarchuk.department;

import com.obodnarchuk.employee.EmployeeService;
import com.obodnarchuk.position.PositionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController {
    private final EmployeeService service;
    private final DepartmentService departmentService;

    public DepartmentController(EmployeeService service, DepartmentService departmentService) {
        this.service = service;
        this.departmentService = departmentService;
    }

    @RequestMapping(value = "{id}/dzialy", method = RequestMethod.GET)
    public ResponseEntity<DepartmentResponseDTO> getDepartment(@PathVariable("id") long id) {
        DepartmentResponseDTO employeeDepartment = service.getEmployeeDepartment(id);
        return new ResponseEntity<>(employeeDepartment, HttpStatus.OK);
    }

    @GetMapping("dzialy")
    public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments() {
        return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);
    }

    @PostMapping(value = "dzialy",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DepartmentResponseDTO> saveDepartment(@RequestBody DepartmentRequestDTO requestDTO) {
        DepartmentResponseDTO responseDTO = departmentService.saveDepartment(requestDTO);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PutMapping(value = "dzialy/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(@PathVariable("id")long id, @RequestBody DepartmentRequestDTO requestDTO) {
        DepartmentResponseDTO responseDTO = departmentService.updateDepartment(id, requestDTO);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @DeleteMapping("dzialy/{id}") // No need, cannot delete FK for Employee
    public HttpStatus deleteDepartment(@PathVariable("id")long id) {
        departmentService.deleteDepartmentById(id);
        return HttpStatus.OK;
    }
}