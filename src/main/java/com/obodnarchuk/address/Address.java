package com.obodnarchuk.address;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Adresy")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;
    @Column(name = "Ulica",length = 30)
    private String street;
    @Column(name = "Nr_Domu", length = 10)
    private String houseNr;
    @Column(name = "Kod_Pocztowy",length = 6)
    private String zipCode;
    @Column(name = "Miasto",length = 30)
    private String city;

    public Address() {
    }

    public Address(String street, String houseNr, String zipCode, String city) {
        this.street = street;
        this.houseNr = houseNr;
        this.zipCode = zipCode;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNr() {
        return houseNr;
    }

    public void setHouseNr(String houseNr) {
        this.houseNr = houseNr;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(houseNr, address.houseNr) && Objects.equals(zipCode, address.zipCode) && Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, houseNr, zipCode, city);
    }
}
