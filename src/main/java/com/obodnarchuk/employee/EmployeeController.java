package com.obodnarchuk.employee;

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

    public EmployeeController(EmployeeService service) {
        this.service = service;
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
        EmployeeResponseDTO responseDTO = service.updateEmployee(id,requestDTO);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteEmployee(@PathVariable long id) {
        service.deleteEmployeeById(id);
        return HttpStatus.OK;
    }

    // Stanowiska
    @GetMapping("/{id}/stanowiska")
    public void updateEmployeePosition(@PathVariable("id") long id, HttpServletResponse response) throws IOException {
        response.sendRedirect("/" + id + "/stanowiska/");
    }

    // Dzialy
    @GetMapping("/{id}/dzialy")
    public void updateEmployeeDepartment(@PathVariable("id") long id, HttpServletResponse response) throws IOException {
        response.sendRedirect("/" + id + "/dzialy/");
    }

    // Adresy
    @GetMapping("/{id}/adresy")
    public void updateEmployeeAddress(@PathVariable("id") long id, HttpServletResponse response) throws IOException {
        response.sendRedirect("/" + id + "/adresy/");
    }
}