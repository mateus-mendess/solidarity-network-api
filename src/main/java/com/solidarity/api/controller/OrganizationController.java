package com.solidarity.api.controller;

import com.solidarity.api.dto.request.OrganizationUpdateRequest;
import com.solidarity.api.model.service.OrganizationService;
import com.solidarity.api.dto.request.OrganizationRequest;
import com.solidarity.api.dto.response.OrganizationResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
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
    public ResponseEntity updateOrganization(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid OrganizationUpdateRequest organizationUpdateRequest) {
        UUID userId = UUID.fromString(jwt.getClaim("userId"));

        organizationService.updateOrganization(userId, organizationUpdateRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity deleteOrganization(@AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getClaim("userId"));

        organizationService.deleteOrganization(userId);

        return ResponseEntity.ok().build();
    }
}
