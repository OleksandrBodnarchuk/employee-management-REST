package com.obodnarchuk.address;

import com.obodnarchuk.department.DepartmentRequestDTO;
import com.obodnarchuk.department.DepartmentResponseDTO;
import com.obodnarchuk.employee.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressController {

    final AddressService addressService;
    final EmployeeService employeeService;

    public AddressController(AddressService addressService, EmployeeService employeeService) {
        this.addressService = addressService;
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "{id}/adresy", method = RequestMethod.GET)
    public ResponseEntity<AddressResponseDTO> getAddress(@PathVariable("id") long id) {
        AddressResponseDTO employeeAddress = employeeService.getEmployeeAddress(id);
        return new ResponseEntity<>(employeeAddress, HttpStatus.OK);
    }

    @GetMapping("adresy")
    public ResponseEntity<List<AddressResponseDTO>> getAllAddresss() {
        return new ResponseEntity<>(addressService.getAllAddresses(), HttpStatus.OK);
    }

    @GetMapping("adresy/{id}")
    public ResponseEntity<AddressResponseDTO> getAllAddresss(@PathVariable("id") long id) {
        AddressResponseDTO addressById = addressService.getAddressById(id);
        return new ResponseEntity<>(addressById, HttpStatus.OK);
    }

    @PostMapping(value = "adresy",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressResponseDTO> saveAddress(@RequestBody AddressRequestDTO requestDTO) {
        AddressResponseDTO responseDTO = addressService.saveAddress(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping(value = "adresy/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressResponseDTO> updateDepartment(@PathVariable("id") long id, @RequestBody AddressRequestDTO requestDTO) {
        AddressResponseDTO responseDTO = addressService.updateAddress(id, requestDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("adresy/{id}") // No need, cannot delete FK for Employee
    public HttpStatus deleteDepartment(@PathVariable("id") long id) {
        addressService.deleteAddressById(id);
        return HttpStatus.OK;
    }
}