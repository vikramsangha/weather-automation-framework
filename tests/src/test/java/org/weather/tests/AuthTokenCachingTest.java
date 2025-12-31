package org.weather.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.weather.core.WeatherClient;
import org.weather.core.WeatherClients;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

class AuthTokenCachingTest extends BaseWireMockTest {

    @Tag("authtest")
    @Test
    void tokenIsFetchedOnlyOnce_whenMultipleApiCallsAreMade() {

        WeatherClient client = WeatherClients.defaultClient();

        client.getCurrentWeather(52.52, 13.405);
        client.getCurrentWeather(52.52, 13.405);

        // OAuth token endpoint must be hit only once
        verify(1, postRequestedFor(urlEqualTo("/oauth/token")));
    }
}
