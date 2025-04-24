package com.wallacy.projetoestagio.util;

import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.UserRepository;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;
import java.util.UUID;

public class TokenUtils {

    private static UserRepository userRepository;

    public static Optional<User> getUserFromToken(JwtAuthenticationToken token) {
        try {
            UUID userId = UUID.fromString(token.getToken().getSubject());
            return userRepository.findByUserId(userId);
        } catch (IllegalArgumentException e) {
            Object userIdClaim = token.getToken().getClaims().get("userId");

            if (userIdClaim instanceof String userIdString) {
                try {
                    UUID userId = UUID.fromString(userIdString);
                    return userRepository.findByUserId(userId);
                } catch (IllegalArgumentException ex) {
                    return Optional.empty();
                }
            }

            return Optional.empty();
        }
    }
}
