package org.weather.core.auth;

import java.time.Instant;

public class AuthToken {

    private final String accessToken;
    private final Instant expiresAt;

    public AuthToken(String accessToken, long expiresInSeconds) {
        this.accessToken = accessToken;
        this.expiresAt = Instant.now().plusSeconds(expiresInSeconds);
    }

    public String value() {
        return accessToken;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt.minusSeconds(5));
    }
}
