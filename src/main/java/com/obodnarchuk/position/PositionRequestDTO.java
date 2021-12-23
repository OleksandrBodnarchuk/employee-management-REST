package com.obodnarchuk.position;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PositionRequestDTO {
    @JsonProperty("title")
    private String title;

    public PositionRequestDTO(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
