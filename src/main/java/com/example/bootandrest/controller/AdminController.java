package com.example.bootandrest.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/")
public class AdminController {

    @GetMapping()
    public String getIndex() {
        return "/index";
    }

}
