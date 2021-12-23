package com.obodnarchuk.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MyExceptionHandler extends RuntimeException{

    private final String RECORD_NOT_FOUND ="RECORD_NOT_FOUND";
    private final String RECORD_ALREADY_EXISTS="RECORD_ALREADY_EXISTS";;

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException
            (RecordNotFoundException ex, WebRequest request)
    {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(RECORD_NOT_FOUND, details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RecordExistsException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException
            (RecordExistsException ex, WebRequest request)
    {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(RECORD_ALREADY_EXISTS, details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
