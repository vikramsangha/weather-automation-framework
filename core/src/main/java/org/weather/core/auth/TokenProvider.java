package org.weather.core.auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenProvider {

    private final AuthClient authClient;
    private AuthToken cachedToken;
    private static final Logger log =
            LoggerFactory.getLogger(TokenProvider.class);


    public TokenProvider(AuthClient authClient) {
        this.authClient = authClient;
    }

    public synchronized AuthToken getToken() {
        if (cachedToken == null || cachedToken.isExpired()) {
            cachedToken = authClient.fetchToken();
        }
        log.info("Cached token expired or missing, fetching new token");
        return cachedToken;
    }
}
