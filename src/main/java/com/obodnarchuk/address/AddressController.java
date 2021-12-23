package com.obodnarchuk.address;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("adresy")
public class AddressController {

    @GetMapping()
    public String getAddress(){
    return "getAddress called";
    }
    @PostMapping()
    public String saveAddress(){
        return "saveAddress called";
    }
    @PutMapping()
    public String updateAddress(){
        return "updateAddress called";
    }
    @DeleteMapping()
    public String deleteAddress(){
        return "deleteAddress called";
    }
}