package com.solidarity.api.controller;

import com.solidarity.api.model.service.VolunteerService;
import com.solidarity.api.dto.request.VolunteerRequest;
import com.solidarity.api.dto.response.VolunteerResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @PostMapping("/register")
    public ResponseEntity<VolunteerResponse> createVolunteer(@ModelAttribute @Valid VolunteerRequest volunteerRequest){
        VolunteerResponse volunteerResponse = volunteerService.save(volunteerRequest);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(volunteerResponse.id()).toUri();
        return ResponseEntity.created(uri).body(volunteerResponse);
    }
}
