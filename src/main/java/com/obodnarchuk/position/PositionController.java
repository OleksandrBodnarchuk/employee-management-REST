package com.obodnarchuk.position;

import com.obodnarchuk.employee.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PositionController {

   private final EmployeeService service;
   private final PositionService positionService;

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

    @PutMapping(value = "stanowiska/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PositionResponseDTO> updatePosition(@PathVariable("id")long id, @RequestBody Position requestDTO) {
        PositionResponseDTO responseDTO = positionService.updatePosition(id, requestDTO);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @DeleteMapping("stanowiska/{id}") // No need, cannot delete FK for Employee
    public HttpStatus deletePosition(@PathVariable("id")long id) {
        positionService.deletePositionById(id);
        return HttpStatus.OK;
    }
}