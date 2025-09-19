package com.solidarity.api.controller;

import com.solidarity.api.dto.request.OrganizationRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @PostMapping(path = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createOrganization(@ModelAttribute @Valid OrganizationRequest organizationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
