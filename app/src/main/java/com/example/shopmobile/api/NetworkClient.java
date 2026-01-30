package com.example.shopmobile.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton-like NetworkClient wrapper.
 * Configured to hit https://fakestoreapi.com/ by default.
 * Supports Base URL swapping for testing with MockWebServer.
 */
public class NetworkClient {
    private static volatile Retrofit retrofit = null;
    
    // Default URL pointing to the real store API
    private static final String DEFAULT_URL = "https://fakestoreapi.com/";
    private static String BASE_URL = DEFAULT_URL;

    public static synchronized Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    // Use standard client for real network calls.
                    // For tests, BaseTest swaps the Base URL to localhost.
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * TEST ONLY: Swaps the Base URL (e.g., to localhost for MockWebServer).
     * Resets the Retrofit instance to ensure the new URL takes effect.
     */
    public static synchronized void setBaseUrl(String url) {
        BASE_URL = url;
        retrofit = null;
    }

    /**
     * Resets the client to use the default production URL.
     */
    public static synchronized void resetClient() {
        BASE_URL = DEFAULT_URL;
        retrofit = null;
    }
}