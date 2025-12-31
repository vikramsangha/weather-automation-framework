package org.weather.tests;

import org.junit.jupiter.api.Test;
import org.weather.core.WeatherClient;
import org.weather.core.WeatherClients;
import org.junit.jupiter.api.Tag;


import static com.github.tomakehurst.wiremock.client.WireMock.*;


class AuthTokenExpiryTest extends BaseWireMockTest {

    @Tag("authexpirycheck")
    @Test
    void tokenIsRefetchedAfterExpiry() throws InterruptedException {

        WeatherClient client = WeatherClients.defaultClient();

        // First call → fetch short-lived token
        client.getCurrentWeather(52.52, 13.405);

        // Wait for token to expire
        Thread.sleep(1500);

        // Second call → must fetch new token
        client.getCurrentWeather(52.52, 13.405);

        verify(2, postRequestedFor(urlEqualTo("/oauth/token")));
    }
}
