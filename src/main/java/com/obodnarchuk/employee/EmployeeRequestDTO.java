package com.obodnarchuk.employee;

import com.obodnarchuk.address.AddressDTO;
import com.obodnarchuk.department.DepartmentRequestDTO;
import com.obodnarchuk.position.Position;

import java.time.LocalDate;

public class EmployeeRequestDTO {
    private String name;
    private String surname;
    private long phone;
    private LocalDate startDate;
    private Integer salary;
    private AddressDTO address;
    private DepartmentRequestDTO department;
    private Position position;

    public EmployeeRequestDTO(String name, String surname, long phone, LocalDate startDate, Integer salary,
                              AddressDTO address, DepartmentRequestDTO department, Position position) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.startDate = startDate;
        this.salary = salary;
        this.address = address;
        this.department = department;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public DepartmentRequestDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentRequestDTO department) {
        this.department = department;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
