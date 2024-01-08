package com.project.eventManagement.service;

import org.springframework.stereotype.Service;

import com.project.eventManagement.controller.LoginResponse;
import com.project.eventManagement.dto.LoginDTO;
import com.project.eventManagement.dto.RegistrationDTO;
import com.project.eventManagement.entity.User;

public interface UserService {

    User registerUser(RegistrationDTO registrationDTO);

    LoginResponse loginUser(LoginDTO loginDTO);

}
