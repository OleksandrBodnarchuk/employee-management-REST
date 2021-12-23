package com.obodnarchuk.department;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dzialy")
public class DepartmentController {

    @GetMapping()
    public String getDepartment(){
    return "getDepartment called";
    }
    @PostMapping()
    public String saveDepartment(){
        return "saveDepartment called";
    }
    @PutMapping()
    public String updateDepartment(){
        return "updateDepartment called";
    }
    @DeleteMapping()
    public String deleteDepartment(){
        return "deleteDepartment called";
    }
}