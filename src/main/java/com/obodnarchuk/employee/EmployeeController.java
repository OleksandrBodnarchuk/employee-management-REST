package com.obodnarchuk.employee;

import com.obodnarchuk.department.DepartmentRequestDTO;
import com.obodnarchuk.department.DepartmentResponseDTO;
import com.obodnarchuk.position.Position;
import com.obodnarchuk.position.PositionResponseDTO;
import com.obodnarchuk.position.PositionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("pracownicy")
public class EmployeeController {

    final EmployeeService service;
    final PositionService positionService;

    public EmployeeController(EmployeeService service, PositionService positionService) {
        this.service = service;
        this.positionService = positionService;
    }

    @GetMapping()
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployee() {
        return new ResponseEntity<>(service.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getOneEmployee(@PathVariable("id") long id) {
        return new ResponseEntity<>(service.getEmployeeById(id), HttpStatus.OK);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponseDTO> saveEmployee(@RequestBody EmployeeRequestDTO requestDTO) {
        EmployeeResponseDTO responseDTO = service.saveEmployee(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable("id") long id, @RequestBody EmployeeRequestDTO requestDTO) {
        EmployeeResponseDTO responseDTO = service.updateEmployee(id, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteEmployee(@PathVariable long id) {
        service.deleteEmployeeById(id);
        return HttpStatus.OK;
    }

    // Stanowiska
    @GetMapping("/{id}/stanowiska")
    public void getEmployeePosition(@PathVariable("id") long id, HttpServletResponse response) throws IOException {
        response.sendRedirect("/" + id + "/stanowiska/");
    }

    @PutMapping("/{id}/stanowiska")
    public ResponseEntity<PositionResponseDTO> updateEmployeePosition(@PathVariable("id") long id, @RequestBody Position positionRequestDTO) {
        PositionResponseDTO positionResponseDTO =  service.updatePosition(id,positionRequestDTO);
        return new ResponseEntity<>(positionResponseDTO,HttpStatus.OK);
    }

    // Dzialy
    @GetMapping("/{id}/dzialy")
    public void getEmployeeDepartment(@PathVariable("id") long id, HttpServletResponse response) throws IOException {
        response.sendRedirect("/" + id + "/dzialy/");
    }

    @PutMapping("/{id}/dzialy")
    public ResponseEntity<DepartmentResponseDTO> updateEmployeeDepartment(@PathVariable("id") long id, @RequestBody DepartmentRequestDTO departmentRequestDTO) {
        DepartmentResponseDTO departmentResponseDTO =  service.updateDepartment(id,departmentRequestDTO);
        return new ResponseEntity<>(departmentResponseDTO,HttpStatus.OK);
    }


    // Adresy
    @GetMapping("/{id}/adresy")
    public void getEmployeeAddress(@PathVariable("id") long id, HttpServletResponse response) throws IOException {
        response.sendRedirect("/" + id + "/adresy/");
    }
}