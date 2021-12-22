package com.obodnarchuk.model;

public class Position {
    private long id;
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
