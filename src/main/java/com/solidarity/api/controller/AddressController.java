package com.solidarity.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {

    @PostMapping("/")
    public ResponseEntity createAddress() {
        //service
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
