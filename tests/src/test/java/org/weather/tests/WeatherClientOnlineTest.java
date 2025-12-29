package org.weather.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.weather.core.WeatherClient;
import org.weather.client.model.ForecastResponse;

import static org.assertj.core.api.Assertions.assertThat;
import org.weather.core.WeatherClients;

class WeatherClientOnlineTest extends BaseWireMockTest {

    @Tag("online")
    @Test
    void shouldFetchCurrentWeather() {

        WeatherClient client = WeatherClients.defaultClient();

        ForecastResponse response =
                client.getCurrentWeather(52.52, 13.405); // Berlin

        assertThat(response).isNotNull();
        assertThat(response.getLatitude()).isNotNull();
        assertThat(response.getLongitude()).isNotNull();

        assertThat(response.getCurrentWeather()).isNotNull();
        assertThat(response.getCurrentWeather().getTemperature()).isNotNull();
        assertThat(response.getCurrentWeather().getWindspeed()).isNotNull();
        assertThat(response.getCurrentWeather().getTime()).isNotBlank();
    }
}
