package com.obodnarchuk.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class RecordExistsException extends RuntimeException {
    public RecordExistsException(String name) {
        super("Record: " + name + " already exists.");
    }

    public RecordExistsException(long id) {
        super("Record with id: " + id + " already exists.");
    }
}
