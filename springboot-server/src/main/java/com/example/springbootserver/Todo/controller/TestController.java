package com.example.springbootserver.todo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
 
    @GetMapping("/test")
    public String tt(){

        return "23";
    }
}
