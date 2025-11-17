package com.obratech.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class IndexControllers {

    @GetMapping({"/index"})
    public String mostrarIndex() {
        return "index"; 
    }
}
