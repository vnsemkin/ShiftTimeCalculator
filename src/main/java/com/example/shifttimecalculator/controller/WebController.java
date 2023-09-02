package com.example.shifttimecalculator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class WebController {
    @GetMapping("/*")
    public String get(@RequestParam(required = false) String name) {
        return Objects.nonNull(name) ? "You send: " + name : "Name is null";
    }
}
