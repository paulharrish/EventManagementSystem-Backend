package com.project.eventManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.eventManagement.dto.LoginDTO;
import com.project.eventManagement.dto.LoginResponseDTO;
import com.project.eventManagement.dto.RegistrationDTO;
import com.project.eventManagement.entity.User;
import com.project.eventManagement.exception.UserNotFoundException;
import com.project.eventManagement.service.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("auth/register")
    public ResponseEntity<User> registerUser(@RequestBody @Valid RegistrationDTO registrationDTO) {
        User user = authenticationService.registerUser(registrationDTO.getFirstName(), registrationDTO.getLastName(),
                registrationDTO.getEmail(), registrationDTO.getUsername(), registrationDTO.getPassword(),
                registrationDTO.getPhone());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("auth/login")
    public LoginResponseDTO loginUser(@RequestBody @Valid LoginDTO loginDTO) throws UserNotFoundException {
        return authenticationService.loginUser(loginDTO.getEmail(), loginDTO.getPassword());
    }

    @GetMapping("auth/logout")
    public ResponseEntity<String> logout(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String token = authenticationService.extractTokenFromRequest(authorizationHeader);
        if (token != null) {
            authenticationService.logout(token);
            return new ResponseEntity<>("Loggged out Successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Missing Authorization header", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("admin/register")
    public ResponseEntity<User> registerAdmin(@RequestBody @Valid RegistrationDTO registrationDTO) {
        User user = authenticationService.registerAdmin(registrationDTO.getFirstName(), registrationDTO.getLastName(),
                registrationDTO.getEmail(), registrationDTO.getUsername(), registrationDTO.getPassword(),
                registrationDTO.getPhone());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}
