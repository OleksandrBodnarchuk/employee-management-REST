package com.obodnarchuk.position;

import javax.persistence.*;


public class PositionResponseDTO {

    private long id;
    private String title;

    public PositionResponseDTO() {
    }

    public PositionResponseDTO(String title) {
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
