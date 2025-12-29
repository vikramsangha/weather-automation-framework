package org.weather.core;

import java.nio.file.Path;
import org.weather.core.auth.*;

public final class WeatherClients {

    private WeatherClients() {}

    public static WeatherClient defaultClient() {
        if (WeatherConfig.isOffline()) {
            return new WeatherClient(WeatherConfig.fixturePath());
        }

        AuthClient authClient =
                new OAuthClientCredentialsAuthClient(
                        "http://localhost:8089/oauth/token",
                        "test-client",
                        "test-secret"
                );

        TokenProvider tokenProvider = new TokenProvider(authClient);
        return new WeatherClient(WeatherConfig.baseUrl(), tokenProvider);
    }
}
