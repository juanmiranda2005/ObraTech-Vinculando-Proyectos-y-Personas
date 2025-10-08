package com.obratech.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class ObratechControllers {
    @Autowired

    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/registro")
    public String registro() {
        return "registro"; 
    }


}
