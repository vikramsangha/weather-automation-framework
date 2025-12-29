package org.weather.tests;

import org.junit.jupiter.api.Test;
import org.weather.core.WeatherClient;
import org.weather.core.WeatherClients;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherClientOfflineIT {

    @Test
    void shouldParseWeatherFromLocalFixture() {

        WeatherClient client = WeatherClients.defaultClient();

        var response = client.getCurrentWeather(0, 0);

        assertThat(response.getLatitude()).isEqualTo(52.52);
        assertThat(response.getCurrentWeather().getTemperature()).isEqualTo(-1.0);
        assertThat(response.getCurrentWeather().getWindspeed()).isEqualTo(7.3);
    }
}
