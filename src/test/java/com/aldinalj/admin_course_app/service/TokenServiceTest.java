/*package com.aldinalj.admin_course_app.service;

import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.aldinalj.admin_course_app.util.JwksUtil;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {

    @Test
    void shouldGenerateValidJwtToken() {
        // Arrange
        RSAKey rsaKey = JwksUtil.generateRSA();
        JWKSource<SecurityContext> jwkSource = (selector, context) -> selector.select(new com.nimbusds.jose.jwk.JWKSet(rsaKey));
        JwtEncoder jwtEncoder = new NimbusJwtEncoder(jwkSource);
        TokenService tokenService = new TokenService(jwtEncoder);

        var authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        var auth = new UsernamePasswordAuthenticationToken("admin@admin.com", null, authorities);

        // Act
        String token = tokenService.generateToken(auth);

        // Assert
        assertNotNull(token);
        assertTrue(token.length() > 10);
        System.out.println("Generated token: " + token);
    }
} */