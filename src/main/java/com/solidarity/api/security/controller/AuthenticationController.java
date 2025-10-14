package com.solidarity.api.security.controller;

import com.solidarity.api.dto.request.AuthenticationRequest;
import com.solidarity.api.dto.response.AuthenticationResponse;
import com.solidarity.api.security.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationService authenticationService, AuthenticationManager authenticationManager) {
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        System.out.println(authenticationRequest.getEmail());
        System.out.println(authenticationRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );

        String token = authenticationService.authenticate(authentication);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(token);

        return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse);
    }
}
