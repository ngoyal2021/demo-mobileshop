package com.example.shopmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.shopmobile.model.Product;
import com.example.shopmobile.util.CartManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivity extends AppCompatActivity {

    private TextView cartBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Product Details");
        }

        Product product = getIntent().getParcelableExtra("product");
        if (product == null) {
            finish();
            return;
        }

        setupViews(product);
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

    private void setupViews(Product product) {
        ImageView imageView = findViewById(R.id.detail_image);
        TextView nameView = findViewById(R.id.detail_name);
        TextView priceView = findViewById(R.id.detail_price);
        TextView categoryView = findViewById(R.id.detail_category);
        TextView descView = findViewById(R.id.detail_description);
        TextView ratingView = findViewById(R.id.detail_rating);
        Button addToCartBtn = findViewById(R.id.btn_add_to_cart);
        FloatingActionButton fabCart = findViewById(R.id.fab_cart);
        cartBadge = findViewById(R.id.cart_badge);

        nameView.setText(product.getName());
        priceView.setText(String.format("$%.2f", product.getPrice()));
        categoryView.setText(product.getCategory());
        descView.setText(product.getDescription());

        // Show rating with count
        String ratingText = String.format("%.1f (%d reviews)", product.getRating(), product.getRatingCount());
        ratingView.setText(ratingText);

        Glide.with(this)
                .load(product.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(imageView);

        addToCartBtn.setOnClickListener(v -> {
            CartManager.getInstance().addItem(product);
            Toast.makeText(this, "Added to cart: " + product.getName(), Toast.LENGTH_SHORT).show();
            updateCartBadge();
        });

        if (fabCart != null) {
            fabCart.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
        }

        updateCartBadge();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (item.getItemId() == R.id.action_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}