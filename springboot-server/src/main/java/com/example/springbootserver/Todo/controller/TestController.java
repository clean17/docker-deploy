package com.example.springbootserver.todo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
 
    @Value("${meta.name}")
    private String key;

    @GetMapping("/test")
    public String tt(){

        return key;
    }
}
