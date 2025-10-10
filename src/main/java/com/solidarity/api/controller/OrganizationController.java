package com.solidarity.api.controller;

import com.solidarity.api.domain.service.OrganizationService;
import com.solidarity.api.dto.request.OrganizationRequest;
import com.solidarity.api.dto.response.OrganizationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<OrganizationResponse>> getOrganization() {
        return ResponseEntity.status(HttpStatus.OK).body(organizationService.findAll());
    }

    @PostMapping("/register")
    public ResponseEntity<OrganizationResponse> createOrganization(@ModelAttribute @Valid OrganizationRequest organizationRequest) {
        OrganizationResponse organizationResponse = organizationService.save(organizationRequest);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(organizationResponse.id()).toUri();
        return ResponseEntity.created(uri).body(organizationResponse);
    }
}
