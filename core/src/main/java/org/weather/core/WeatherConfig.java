package org.weather.core;

public final class WeatherConfig {

    private WeatherConfig() {}

    public static String baseUrl() {
        return System.getProperty(
                "weather.baseUrl",
                "https://api.open-meteo.com"
        );
    }

    public static String fixturePath() {
        return System.getProperty("weather.fixture");
    }

    public static boolean isOffline() {
        return fixturePath() != null;
    }
}
