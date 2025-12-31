package org.weather.core.domain;

public final class WeatherRequest {

    private final double latitude;
    private final double longitude;

    public WeatherRequest(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double latitude() {
        return latitude;
    }

    public double longitude() {
        return longitude;
    }
}
