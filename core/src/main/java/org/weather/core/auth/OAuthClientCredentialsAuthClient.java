package org.weather.core.auth;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OAuthClientCredentialsAuthClient implements AuthClient {

    private final String tokenUrl;
    private final String clientId;
    private final String clientSecret;
    private static final Logger log = LoggerFactory.getLogger(OAuthClientCredentialsAuthClient.class);


    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public OAuthClientCredentialsAuthClient(
            String tokenUrl,
            String clientId,
            String clientSecret
    ) {
        this.tokenUrl = tokenUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public AuthToken fetchToken() {
        try {
            String body = "grant_type=client_credentials"
                    + "&client_id=" + clientId
                    + "&client_secret=" + clientSecret;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(tokenUrl))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Token request failed: " + response.body());
            }

            Map<?, ?> json = gson.fromJson(response.body(), Map.class);

            String accessToken = (String) json.get("access_token");
            Double expiresIn = (Double) json.get("expires_in");
            log.info("Fetching OAuth token from {}", tokenUrl);

            return new AuthToken(accessToken, expiresIn.longValue());

        } catch (Exception e) {
            log.error("Failed to fetch OAuth token", e);
            throw new RuntimeException("Failed to fetch OAuth token", e);
        }
    }
}
