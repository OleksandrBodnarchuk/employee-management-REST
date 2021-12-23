package com.obodnarchuk.department;

import com.obodnarchuk.address.AddressDTO;


public class DepartmentDTO {
    private String name;
    private AddressDTO address;

    public DepartmentDTO(String name, AddressDTO address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

}
