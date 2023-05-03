package com.baloot.controller;

import com.baloot.model.LoginInfo;
import com.baloot.model.RegisterInfo;
import com.baloot.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginInfo loginData) throws IOException {
        System.out.println("in login");
        try {
            AuthService.authenticateUser(loginData);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Logging in failed."+e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout() {
        try {
            AuthService.logoutUser();
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterInfo registerData) throws IOException {
        System.out.println("in signup");
        try {
            AuthService.registerUser(registerData);
            System.out.println("signed up successfully");
            return ResponseEntity.status(HttpStatus.OK).body("OK - Registered successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Register Failed." + e.getMessage());
        }
    }
}
