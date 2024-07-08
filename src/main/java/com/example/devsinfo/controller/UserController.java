package com.example.devsinfo.controller;

import com.example.devsinfo.DTO.LoginDTO;
import com.example.devsinfo.DTO.UserDTO;
import com.example.devsinfo.DTO.response.LoginResponse;
import com.example.devsinfo.models.User;
import com.example.devsinfo.services.JwtService;
import com.example.devsinfo.services.UserService;
import com.example.devsinfo.utils.GenericResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDTO userDTO) {
        userService.signup(userDTO);
        GenericResponse genericResponse = new GenericResponse("User signup successful");
        return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginDTO loginDTO) {
        User authenticatedUser = userService.loginUser(loginDTO);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .build();

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

}
