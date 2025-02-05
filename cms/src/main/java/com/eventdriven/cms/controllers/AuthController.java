package com.eventdriven.cms.controllers;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventdriven.cms.services.AuthService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eventdriven.cms.domain.AppUser;
import com.eventdriven.cms.requestDTO.LoginUserDTO;
import com.eventdriven.cms.requestDTO.RegisterUserDTO;
import com.eventdriven.cms.response.LoginUserResponseDTO;
import com.eventdriven.cms.response.RegisterUserResponseDTO;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDTO> RegisterUser(@RequestBody RegisterUserDTO body) {
        AppUser user = authService.registerUser(body);
        RegisterUserResponseDTO response = new RegisterUserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole().name());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> LoginUser(@RequestBody LoginUserDTO body) {
        HashMap<String, LoginUserResponseDTO.Userlogin> loginObj = authService.loginUser(body);
        String token = loginObj.keySet().iterator().next();
        LoginUserResponseDTO.Userlogin user = loginObj.get(token);

        LoginUserResponseDTO response = new LoginUserResponseDTO();
        response.setToken(token);
        response.setMessage("User Login successful!");
        response.setUser(user);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
