package com.example.security_app.service.impl;

import com.example.security_app.dto.request.Signin;
import com.example.security_app.dto.request.Signup;
import com.example.security_app.dto.response.JwtResponse;
import com.example.security_app.model.User;
import com.example.security_app.repository.UserRepository;
import com.example.security_app.service.AuthenicationService;
import com.example.security_app.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenicationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public JwtResponse signup(Signup request) {
        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return new JwtResponse(jwt);
    }

    @Override
    public JwtResponse signin(Signin request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Email"));
            var jwt = jwtService.generateToken(user);
            return new JwtResponse(jwt);
        }catch(Exception e){
            System.out.println("Invalid credentials");
        }
        return new JwtResponse("cannot generate token");
    }
}
