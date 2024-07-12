package com.example.security_app.controller;

import com.example.security_app.dto.request.Signin;
import com.example.security_app.dto.request.Signup;
import com.example.security_app.dto.response.JwtResponse;
import com.example.security_app.service.AuthenicationService;
import com.example.security_app.service.JwtService;
import com.example.security_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private AuthenicationService authenicationService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public JwtResponse userSignup(@RequestBody Signup request){
        return authenicationService.signup(request);
    }

    @PostMapping("/signin")
    public JwtResponse userSignin(@RequestBody Signin request){
        return authenicationService.signin(request);
    }
}
