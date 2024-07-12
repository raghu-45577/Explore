package com.example.security_app.service;

import com.example.security_app.dto.request.Signin;
import com.example.security_app.dto.request.Signup;
import com.example.security_app.dto.response.JwtResponse;

public interface AuthenicationService {

    JwtResponse signup(Signup request);

    JwtResponse signin(Signin request);
}
