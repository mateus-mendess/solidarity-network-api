package com.solidarity.api.controller;

import com.solidarity.api.model.service.VolunteerService;
import com.solidarity.api.dto.request.VolunteerRequest;
import com.solidarity.api.dto.response.VolunteerResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<VolunteerResponse>> getAllVolunteers() {
        return ResponseEntity.status(HttpStatus.OK).body(volunteerService.getAllVolunteers());
    }

    @GetMapping("/profile")
    public ResponseEntity<VolunteerResponse> getVolunteerById(@AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getClaim("userId"));

        return ResponseEntity.status(HttpStatus.OK).body(volunteerService.findVolunteerById(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<VolunteerResponse> createVolunteer(@ModelAttribute @Valid VolunteerRequest volunteerRequest){
        VolunteerResponse volunteerResponse = volunteerService.saveVolunteer(volunteerRequest);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(volunteerResponse.id()).toUri();

        return ResponseEntity.created(uri).body(volunteerResponse);
    }

    @PatchMapping
    public ResponseEntity updateVolunteer(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid VolunteerRequest volunteerRequest) {
        UUID userId = UUID.fromString(jwt.getClaim("userId"));

        volunteerService.updateVolunteer(userId, volunteerRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity deleteVolunteer(@AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getClaim("userId"));

        volunteerService.deleteVolunteer(userId);

        return ResponseEntity.ok().build();
    }
}
