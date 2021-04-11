package com.example.lightingmcqueenecommerce.api.controllers;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String hello() {
        return "hello World";
    }




}
