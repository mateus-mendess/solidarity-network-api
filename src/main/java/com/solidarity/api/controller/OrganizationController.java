package com.solidarity.api.controller;

import com.solidarity.api.model.service.OrganizationService;
import com.solidarity.api.dto.request.OrganizationRequest;
import com.solidarity.api.dto.response.OrganizationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<OrganizationResponse> createOrganization(@RequestPart("organization") @Valid OrganizationRequest organizationRequest,
                                                                   @RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto,
                                                                   @RequestPart(value = "coverPhoto", required = false) MultipartFile coverPhoto) {

        organizationRequest.setProfilePhoto(profilePhoto);
        organizationRequest.setCoverPhoto(coverPhoto);

        OrganizationResponse organizationResponse = organizationService.save(organizationRequest);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(organizationResponse.id()).toUri();
        return ResponseEntity.created(uri).body(organizationResponse);
    }

    @PatchMapping
    public ResponseEntity<OrganizationResponse> updateOrganization(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid OrganizationRequest organizationRequest) {
        UUID userId = UUID.fromString(jwt.getClaim("id"));

        organizationService.updateOrganization(userId, organizationRequest);

        return ResponseEntity.ok().build();
    }
}
