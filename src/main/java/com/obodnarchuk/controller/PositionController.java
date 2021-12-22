package com.obodnarchuk.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pozycje")
public class PositionController {

    @GetMapping()
    public String getPosition(){
    return "getPosition called";
    }
    @PostMapping()
    public String savePosition(){
        return "savePosition called";
    }
    @PutMapping()
    public String updatePosition(){
        return "updatePosition called";
    }
    @DeleteMapping()
    public String deletePosition(){
        return "deletePosition called";
    }
}