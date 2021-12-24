package com.obodnarchuk.department;

import com.obodnarchuk.address.Address;

public class DepartmentRequestDTO {
    private String name;
    private Address address;

    public DepartmentRequestDTO(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
