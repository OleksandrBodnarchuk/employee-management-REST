package com.obodnarchuk.employee;

import com.obodnarchuk.address.Address;
import com.obodnarchuk.department.Department;
import com.obodnarchuk.position.Position;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Pracownicy")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;
    @Column(name = "Imie", length = 30)
    private String name;
    @Column(name = "Nazwisko", length = 30)
    private String surname;
    @Column(name = "Email", unique = true, length = 80)
    private String email;
    @Column(name = "Telefon")
    private long phone;
    @Column(name = "Data_Zatrudnienia")
    private LocalDate startDate;
    @Column(name = "Wynagrodzenie")
    private Integer salary;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "Adres")
    private Address address;

    @OneToOne(optional = true, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "Dzial")
    private Department department;

    @OneToOne(optional = true, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "Stanowisko")
    private Position position;

    public Employee() {

    }

    public Employee(String name, String surname, String email, Long phone, LocalDate startDate, Integer salary) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.startDate = startDate;
        this.salary = salary;
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

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
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

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name) && Objects.equals(surname, employee.surname) && Objects.equals(email, employee.email) && Objects.equals(phone, employee.phone) && Objects.equals(startDate, employee.startDate) && Objects.equals(salary, employee.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, email, phone, startDate, salary);
    }

    @Override
    public String toString() {
        return id + "," +
                name + "," +
                surname + "," +
                email + "," +
                phone + "," +
                salary + "," +
                startDate + "," +
                department.getId() + "," +
                department.getName() + "," +
                department.getAddress().getId() + "," +
                department.getAddress().getStreet() + "," +
                department.getAddress().getHouseNr() + "," +
                department.getAddress().getCity() + "," +
                department.getAddress().getZipCode() + "," +
                address.getId() + "," +
                address.getStreet() + "," +
                address.getHouseNr() + "," +
                address.getStreet() + "," +
                address.getCity() + "," +
                address.getZipCode() + "," +
                position.getId() + "," +
                position.getTitle();
    }
}
