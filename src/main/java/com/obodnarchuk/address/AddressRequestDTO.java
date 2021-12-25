package com.obodnarchuk.address;


public class AddressRequestDTO {
    private String street;
    private String houseNr;
    private String zipCode;
    private String city;

    public AddressRequestDTO(String street, String houseNr, String zipCode, String city) {
        this.street = street;
        this.houseNr = houseNr;
        this.zipCode = zipCode;
        this.city = city;
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
    public String toString() {
        return "{" +
                "street='" + street + '\'' +
                ", houseNr='" + houseNr + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
