package com.lubucketz.stylistai.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
public class JwtConfig {

    @Value("${JWT_SECRET}") //move to config and then create a class to retrieve values
    private String jwtSecret;

    @Bean
    public JwtEncoder encoder() {
        SecretKey key = new SecretKeySpec(
                jwtSecret.getBytes(),
                "HmacSHA256"
        );

        return new NimbusJwtEncoder(
                new ImmutableSecret<>(key)
        );
    }
}
