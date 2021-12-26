package com.obodnarchuk.employee;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class EmployeeSalaryDbDTO {

    @JsonProperty("stanowisko")
    private String position;
    @JsonProperty("staz_pracy")
    private int seniority;
    @JsonProperty("srednie_wynagrodzenie")
    private int salary;

    public EmployeeSalaryDbDTO(String position, int seniority, int salary) {
        this.position = position;
        this.salary = salary;
        setSeniority(seniority);
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSeniority() {
        return seniority;
    }

    private void setSeniority(int seniority) {
        this.seniority = LocalDate.now().getYear() - seniority + 1;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
