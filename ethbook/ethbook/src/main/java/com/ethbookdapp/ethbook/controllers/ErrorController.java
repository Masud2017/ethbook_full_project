package com.ethbookdapp.ethbook.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {
    @GetMapping(value = "/error")
    public String error() {
        return "This is a error page";
    }
}
