package com.example.security_app.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;

@RestController
@RequestMapping("/message")
public class messageController {

    @GetMapping("/hello")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public String sayHello(){
        return "Hello Welcome!!!!";
    }

    @GetMapping("/{user}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String greetUser(@PathVariable String user){

        return "Hello "+ user;
    }

    @GetMapping("/secret")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String greetAdmin(){
        return "Hello Admin";
    }
}
