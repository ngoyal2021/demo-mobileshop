package com.example.shopmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmobile.adapter.ProductAdapter;
import com.example.shopmobile.api.MockServerManager;
import com.example.shopmobile.api.NetworkClient;
import com.example.shopmobile.api.ProductApi;
import com.example.shopmobile.model.Product;
import com.example.shopmobile.util.CartManager;
import com.example.shopmobile.util.EspressoIdlingResource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ProgressBar progressBar;
    private LinearLayout errorLayout;
    private TextView errorText;
    private Button btnRetry;
    private FloatingActionButton fabCart;
    private TextView cartBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        errorLayout = findViewById(R.id.error_layout);
        errorText = findViewById(R.id.error_text);
        btnRetry = findViewById(R.id.btn_retry);
        fabCart = findViewById(R.id.fab_cart);
        cartBadge = findViewById(R.id.cart_badge);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new ProductAdapter(this, new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        btnRetry.setOnClickListener(v -> fetchProducts());

        if (fabCart != null) {
            fabCart.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
        }

        handleMockServerAndFetch();
    }

    private void handleMockServerAndFetch() {
        // If configured for Mock Server AND NOT running inside an automated test (BaseTest)
        // BaseTest passes "IS_TEST_RUN" to prevent the app from hijacking the mock server logic.
        boolean isTestRun = getIntent().getBooleanExtra("IS_TEST_RUN", false);

        if (BuildConfig.USE_MOCK_SERVER && !isTestRun) {
            // Start the mock server on a background thread
            new Thread(() -> {
                // Pass Application Context to read assets
                MockServerManager.getInstance().start(getApplicationContext());
                // After server is started and URL is set, fetch data on UI thread
                runOnUiThread(this::fetchProducts);
            }).start();
        } else {
            // Normal flow (Real backend OR Automated Test handling its own server)
            fetchProducts();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
    }

    private void updateCartBadge() {
        if (cartBadge != null) {
            int count = CartManager.getInstance().getItemCount();
            if (count > 0) {
                cartBadge.setText(String.valueOf(count));
                cartBadge.setVisibility(View.VISIBLE);
            } else {
                cartBadge.setVisibility(View.GONE);
            }
        }
    }

    private void fetchProducts() {
        progressBar.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        // Notify Espresso to wait
        EspressoIdlingResource.increment();

        ProductApi api = NetworkClient.getClient().create(ProductApi.class);
        Call<List<Product>> call = api.getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    adapter.updateData(response.body());
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    showError("Failed to load products. Server returned code: " + response.code());
                }
                EspressoIdlingResource.decrement();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showError("Network Error. Please check your internet connection.");
                EspressoIdlingResource.decrement();
            }
        });
    }

    private void showError(String message) {
        errorText.setText(message);
        errorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}