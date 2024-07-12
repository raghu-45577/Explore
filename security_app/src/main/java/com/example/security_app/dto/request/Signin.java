package com.example.security_app.dto.request;


public class Signin {

    private String email;
    private String password;

    public Signin() {
    }

    public Signin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
