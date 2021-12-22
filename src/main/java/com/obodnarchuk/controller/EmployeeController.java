package com.obodnarchuk.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pracownicy")
public class EmployeeController {

    @GetMapping()
    public String getEmployee(){
    return "getEmployee called";
    }
    @PostMapping()
    public String saveEmployee(){
        return "saveEmployee called";
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