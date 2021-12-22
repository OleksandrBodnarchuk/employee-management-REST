package com.obodnarchuk.controller;

import com.obodnarchuk.model.Employee;
import com.obodnarchuk.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pracownicy")
public class EmployeeController {

    final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Employee>> getEmployee(){
    return new ResponseEntity<>(service.getAllEmployees(),HttpStatus.OK);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee){
        return new ResponseEntity<>(service.saveEmployee(employee),HttpStatus.OK);
    }
    @PutMapping()
    public String updateEmployee(){
        return "updateEmployee called";
    }
    @DeleteMapping()
    public String deleteEmployee(){
        return "deleteEmployee called";
    }
}