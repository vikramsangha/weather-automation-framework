package org.weather.core;

import java.nio.file.Path;

public final class WeatherConfig {

    private WeatherConfig() {}

    public static String baseUrl() {
        return System.getProperty(
                "weather.baseUrl",
                "https://api.open-meteo.com"
        );
    }

    public static boolean isOffline() {
        String fixture = System.getProperty("weather.fixture");
        return fixture != null && !fixture.isBlank();
    }


    public static Path fixturePath() {
        String path = System.getProperty("weather.fixture");
        if (path == null || path.isBlank()) {
            throw new IllegalStateException(
                    "Offline mode enabled but weather.fixture is not set"
            );
        }
        return Path.of(path);
    }

    public static boolean isHttpLoggingEnabled() {
        return Boolean.parseBoolean(
                System.getProperty("weather.http.logging", "true")
        );
    }

}
