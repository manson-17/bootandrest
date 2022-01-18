package com.example.bootandrest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class AdminController {

    @GetMapping()
    public String getIndex() {
        return "/index";
    }

}
