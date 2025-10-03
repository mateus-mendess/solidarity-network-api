package com.solidarity.api.controller;

import com.solidarity.api.domain.service.OrganizationService;
import com.solidarity.api.dto.request.AddressRequest;
import com.solidarity.api.dto.request.OrganizationRequest;
import com.solidarity.api.dto.response.OrganizationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping(path = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<OrganizationResponse> createOrganization(@RequestPart("organization") @Valid OrganizationRequest organizationRequest,
                                                                   @RequestPart(value = "profilePhoto", required = false)MultipartFile profilePhoto,
                                                                   @RequestPart(value = "profilePhoto", required = false) MultipartFile coverPhoto) throws IOException, InterruptedException {

        organizationRequest.setProfilePhoto(profilePhoto);
        organizationRequest.setCoverPhoto(coverPhoto);
        OrganizationResponse organizationResponse = organizationService.save(organizationRequest);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(organizationResponse.id()).toUri();
        return ResponseEntity.created(uri).body(organizationResponse);
    }
}
