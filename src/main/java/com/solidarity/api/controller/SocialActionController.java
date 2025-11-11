package com.solidarity.api.controller;

import com.solidarity.api.dto.request.SocialActionRequest;
import com.solidarity.api.dto.response.SocialActionCardResponse;
import com.solidarity.api.model.service.SocialActionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/social-action")
public class SocialActionController {

    private final SocialActionService socialActionService;

    public SocialActionController(SocialActionService socialActionService) {
        this.socialActionService = socialActionService;
    }

    @PostMapping("/register")
    public ResponseEntity<SocialActionCardResponse> createSocialAction(@RequestPart("socialAction") @Valid SocialActionRequest socialActionRequest,
                                                                       @RequestPart(value = "image", required = false) MultipartFile image,
                                                                       @AuthenticationPrincipal Jwt jwt) {

        UUID organizationId = UUID.fromString(jwt.getClaim("userId"));

        SocialActionCardResponse socialActionCardResponse = socialActionService.save(socialActionRequest, image, organizationId);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand().toUri();
        return ResponseEntity.created(uri).body(socialActionCardResponse);
    }
}
