package com.obodnarchuk.employee;

import com.obodnarchuk.address.AddressDTO;
import com.obodnarchuk.department.DepartmentRequestDTO;
import com.obodnarchuk.position.Position;

import java.util.Date;

public class EmployeeResponseDTO {
    private long id;
    private String name;
    private String surname;
    private String email;
    private long phone;
    private Date startDate;
    private Integer salary;
    private AddressDTO address;
    private DepartmentRequestDTO department;
    private Position position;

    public EmployeeResponseDTO(long id, String name, String surname, String email,
                               long phone, Date startDate, Integer salary, AddressDTO address,
                               DepartmentRequestDTO department, Position position) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.startDate = startDate;
        this.salary = salary;
        this.address = address;
        this.department = department;
        this.position = position;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
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
