package org.weather.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.weather.core.WeatherClient;

import static org.assertj.core.api.Assertions.assertThat;
import org.weather.core.WeatherClients;
import org.weather.core.domain.WeatherRequest;
import org.weather.core.domain.WeatherRequestBuilder;
import org.junit.jupiter.api.DisplayName;

import java.util.stream.Stream;

class WeatherClientTest extends BaseWireMockTest {

    @DisplayName("Weather Check")
    @Tag("weather_check")
    @ParameterizedTest(name = "{0}")
    @MethodSource("testData")
    void shouldValidateWeatherForCity(String city, double latitude, double longitude){
        WeatherClient client = WeatherClients.defaultClient();

        var request = WeatherRequestBuilder.with()
                .coordinates(52.52, 13.405)
                .build();

        var response = client.getWeather(request);


        assertThat(response.getLatitude()).isNotNull();
        assertThat(response.getCurrentWeather().getTemperature()).isNotNull();
    }

    static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of("Berlin", 52.52, 13.405),
                Arguments.of("Munich", 48.1351, 11.5820),
                Arguments.of("London", 51.5072, -0.1276)
        );
    }
}
