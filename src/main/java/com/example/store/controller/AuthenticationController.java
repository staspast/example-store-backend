package com.example.store.controller;


import com.example.store.dto.RegisterRequestDto;
import com.example.store.model.entity.User;
import com.example.store.rest.RestResponse;
import com.example.store.service.UserService;
import com.example.store.validation.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;

//@CrossOrigin(origins = "http://localhost", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class AuthenticationController {

    @Autowired
    private UserService userService;



    @PostMapping(value = "/register")
    public ResponseEntity<RestResponse> registerUser(@RequestBody RegisterRequestDto user) {

        return userService.register(user);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<RestResponse> login(@RequestBody User login) throws ServletException {
       return userService.login(login);
    }

    @GetMapping(value = "/login")
    public String getResponse() throws ServletException {
       return "Hello";
    }
}
