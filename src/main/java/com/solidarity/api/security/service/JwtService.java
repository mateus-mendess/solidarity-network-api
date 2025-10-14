package com.solidarity.api.security.service;

import com.solidarity.api.security.model.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private static final Long TOKEN_EXPIRATION_SECONDS = 10800L;

    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generatorToken(Authentication authentication) {
        Instant now = Instant.now();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String scopes = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("solidarity-network")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(TOKEN_EXPIRATION_SECONDS))
                .subject(authentication.getName())
                .claim("scope", scopes)
                .claim("userId", userDetails.getId().toString())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
