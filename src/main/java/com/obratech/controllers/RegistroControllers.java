package com.obratech.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistroControllers {    
      @GetMapping("/registro") 
    public String mostrarRegistro() {
        return "registro";
    }
}
