package com.obodnarchuk.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CSVFileError extends RuntimeException {
    public CSVFileError(String export) {
        super("Error while file: "+export);
    }
}
