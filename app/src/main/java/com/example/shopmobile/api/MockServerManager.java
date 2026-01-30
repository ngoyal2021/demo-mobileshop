package com.example.shopmobile.api;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class MockServerManager {
    private static MockServerManager instance;
    private MockWebServer server;
    private static final String TAG = "MockServerManager";

    private MockServerManager() {}

    public static synchronized MockServerManager getInstance() {
        if (instance == null) {
            instance = new MockServerManager();
        }
        return instance;
    }

    public void start(Context context) {
        if (server != null) {
            Log.d(TAG, "MockWebServer already running on " + server.url("/"));
            return;
        }

        // Load JSON from assets once
        final String jsonResponse = loadJSONFromAsset(context, "mock_products.json");

        try {
            server = new MockWebServer();
            server.setDispatcher(new Dispatcher() {
                @Override
                public MockResponse dispatch(RecordedRequest request) {
                    // Simple logic: Always return the product list for any request
                    return new MockResponse()
                            .setResponseCode(200)
                            .setBody(jsonResponse);
                }
            });
            server.start();
            String url = server.url("/").toString();
            Log.d(TAG, "MockWebServer started at " + url);

            // Point the app's NetworkClient to localhost
            NetworkClient.setBaseUrl(url);

        } catch (Exception e) {
            Log.e(TAG, "Failed to start MockWebServer", e);
        }
    }

    public void shutdown() {
        if (server != null) {
            try {
                server.shutdown();
                server = null;
            } catch (Exception e) {
                Log.e(TAG, "Error shutting down server", e);
            }
        }
    }

    private String loadJSONFromAsset(Context context, String filename) {
        try (InputStream is = context.getAssets().open(filename)) {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            return new String(buffer);
        } catch (IOException ex) {
            Log.e(TAG, "Error reading mock data asset", ex);
            return "[]";
        }
    }
}