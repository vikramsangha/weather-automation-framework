package org.weather.core.domain;

public final class WeatherRequestBuilder {

    private Double latitude;
    private Double longitude;

    private WeatherRequestBuilder() {}

    public static WeatherRequestBuilder with() {
        return new WeatherRequestBuilder();
    }

    public WeatherRequestBuilder coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        return this;
    }

    public WeatherRequest build() {
        if (latitude == null || longitude == null) {
            throw new IllegalStateException("Coordinates must be set");
        }
        return new WeatherRequest(latitude, longitude);
    }
}
