package com.obodnarchuk.department;

import com.obodnarchuk.address.Address;

public class DepartmentResponseDTO {
    private final long id;
    private String name;
    private Address address;

    public DepartmentResponseDTO(long id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public long getId() {
        return id;
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
