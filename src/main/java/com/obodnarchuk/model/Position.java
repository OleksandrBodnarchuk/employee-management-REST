package com.obodnarchuk.model;

import javax.persistence.*;

@Entity
@Table(name = "Stanowiska")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;
    @Column(name = "Nazwa", length = 50)
    private String title;

    public Position() {
    }

    public Position(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
