package org.weather.core;

import org.weather.client.api.DefaultApi;
import org.weather.client.invoker.ApiClient;
import org.weather.client.model.ForecastResponse;
import java.nio.file.Path;
import java.nio.file.Files;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import org.weather.core.auth.*;
import org.weather.core.domain.WeatherRequest;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherClient {

    private final DefaultApi api;
    private final Path fixturePath;
    private static final Logger log =
            LoggerFactory.getLogger(WeatherClient.class);

    public WeatherClient(String baseUrl, TokenProvider tokenProvider) {
        log.info("Starting WeatherClient in PROD mode (live API)");
        log.info("Base URL: {}", baseUrl);
        ApiClient apiClient = new ApiClient();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (!WeatherConfig.isOffline()) {
            builder.addInterceptor(new OAuthInterceptor(tokenProvider));
        }

        if (WeatherConfig.isHttpLoggingEnabled()) {
            HttpLoggingInterceptor logging =
                    new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        apiClient.setHttpClient(builder.build());
        apiClient.setBasePath(baseUrl);

        this.api = new DefaultApi(apiClient);
        this.fixturePath = null;
    }


    public WeatherClient(Path jsonFixture) {
        log.info("Starting WeatherClient in AQA mode (fixtures)");
        log.info("Using fixture file: {}", jsonFixture.toAbsolutePath());
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

    public ForecastResponse getWeather(WeatherRequest request) {
        return getCurrentWeather(request.latitude(), request.longitude());
    }


}
