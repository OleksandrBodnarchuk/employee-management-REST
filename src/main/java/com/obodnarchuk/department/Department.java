package com.obodnarchuk.department;

import com.obodnarchuk.address.Address;

import javax.persistence.*;

@Entity
@Table(name = "Dzialy")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;
    @Column(name = "Nazwa",length = 30)
    private String name;

    @OneToOne(optional = true, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "Localizacja")
    private Address address;

    public Department() {
    }

    public Department(String name) {
        this.name = name;
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
}
