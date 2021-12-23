package com.obodnarchuk.position;

import com.obodnarchuk.employee.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PositionController {

    final EmployeeService service;
    final PositionService positionService;

    public PositionController(EmployeeService service, PositionService positionService) {
        this.service = service;
        this.positionService = positionService;
    }

    @RequestMapping(value = "{id}/stanowiska", method = RequestMethod.GET)
    public ResponseEntity<PositionResponseDTO> getPosition(@PathVariable("id") long id) {
        PositionResponseDTO employeePosition = service.getEmployeePosition(id);
        return new ResponseEntity<>(employeePosition, HttpStatus.OK);
    }

    @PostMapping(value = "stanowiska",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PositionResponseDTO> savePosition(@RequestBody Position requestDTO) {
        PositionResponseDTO responseDTO = positionService.savePosition(requestDTO);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
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