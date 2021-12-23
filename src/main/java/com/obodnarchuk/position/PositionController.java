package com.obodnarchuk.position;

import com.obodnarchuk.employee.EmployeeResponseDTO;
import com.obodnarchuk.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PositionController {

    final EmployeeService service;

    public PositionController(EmployeeService service) {
        this.service = service;
    }

    @RequestMapping(value = "{id}/pozycje", method = RequestMethod.GET)
    public ResponseEntity<PositionResponseDTO> getPosition(@PathVariable("id") long id) {
        PositionResponseDTO employeePosition = service.getEmployeePosition(id);
        return new ResponseEntity<>(employeePosition, HttpStatus.OK);
    }

    @PostMapping()
    public String savePosition() {
        return "savePosition called";
    }

    @PutMapping()
    public String updatePosition() {
        return "updatePosition called";
    }

    @DeleteMapping()
    public String deletePosition() {
        return "deletePosition called";
    }
}