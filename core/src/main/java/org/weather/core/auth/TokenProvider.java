package org.weather.core.auth;

public class TokenProvider {

    private final AuthClient authClient;
    private AuthToken cachedToken;

    public TokenProvider(AuthClient authClient) {
        this.authClient = authClient;
    }

    public synchronized AuthToken getToken() {
        if (cachedToken == null || cachedToken.isExpired()) {
            cachedToken = authClient.fetchToken();
        }
        return cachedToken;
    }
}
