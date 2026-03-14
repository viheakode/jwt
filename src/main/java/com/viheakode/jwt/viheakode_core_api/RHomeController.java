package com.viheakode.jwt.viheakode_core_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RHomeController {
    
    @GetMapping
    public String home(){
        return "Home page";
    }

    @GetMapping("/user")
    public String user(){
        return "User page";
    }

    @GetMapping("/admin")
    public String admin(){
        return "Admin page";
    }

    @GetMapping("/tester")
    public String tester(){
        return "Tester page";
    }

    @GetMapping("/anonymous")
    public String anonymous(){
        return "Anonymous page";
    }
}
