package org.weather.core;

import java.nio.file.Path;

public final class WeatherClients {

    private WeatherClients() {}

    public static WeatherClient defaultClient() {
        if (WeatherConfig.isOffline()) {
            return new WeatherClient(Path.of(WeatherConfig.fixturePath()));
        }
        return new WeatherClient(WeatherConfig.baseUrl());
    }
}
