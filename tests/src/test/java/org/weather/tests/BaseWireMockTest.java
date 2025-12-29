package org.weather.tests;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public abstract class BaseWireMockTest {

    protected static WireMockServer wireMockServer;

    @BeforeAll
    static void startWireMock() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();

        configureFor("localhost", 8089);

        stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                              "access_token": "mock-token-123",
                              "token_type": "Bearer",
                              "expires_in": 300
                            }
                        """)));
    }

    @AfterAll
    static void stopWireMock() {
        wireMockServer.stop();
    }
}
