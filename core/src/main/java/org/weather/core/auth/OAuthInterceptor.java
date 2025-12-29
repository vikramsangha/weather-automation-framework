package org.weather.core.auth;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OAuthInterceptor implements Interceptor {

    private final TokenProvider tokenProvider;

    public OAuthInterceptor(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        AuthToken token = tokenProvider.getToken();

        Request authenticatedRequest = chain.request().newBuilder()
                .header("Authorization", "Bearer " + token.value())
                .build();

        return chain.proceed(authenticatedRequest);
    }
}
