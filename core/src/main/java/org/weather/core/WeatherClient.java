package org.weather.core;

import org.weather.client.api.DefaultApi;
import org.weather.client.invoker.ApiClient;
import org.weather.client.model.ForecastResponse;
import java.nio.file.Path;
import java.nio.file.Files;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import org.weather.core.auth.*;

public class WeatherClient {

    private final DefaultApi api;
    private final Path fixturePath;


    public WeatherClient(String baseUrl, TokenProvider tokenProvider) {
        ApiClient apiClient = new ApiClient();

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new OAuthInterceptor(tokenProvider))
                .build();

        apiClient.setHttpClient(httpClient);
        apiClient.setBasePath(baseUrl);

        this.api = new DefaultApi(apiClient);
        this.fixturePath = null;
    }

    public WeatherClient(Path jsonFixture) {
        this.api = null;
        this.fixturePath = jsonFixture;
    }

    private ForecastResponse loadFromFile(Path path) {
        try {
            String json = Files.readString(path);
            Gson gson = new Gson();
            return gson.fromJson(json, ForecastResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load fixture: " + path, e);
        }
    }


    /**
     * Fetch current weather for given coordinates.
     * This is the ONLY method tests will use.
     */
    public ForecastResponse getCurrentWeather(double latitude, double longitude) {
        try {
            if (fixturePath != null) {
                return loadFromFile(fixturePath);
            }
            return api.v1ForecastGet(latitude, longitude, true);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch weather data", e);
        }
    }

}
