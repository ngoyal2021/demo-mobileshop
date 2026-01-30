package com.example.shopmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmobile.adapter.CartAdapter;
import com.example.shopmobile.model.CartItem;
import com.example.shopmobile.util.CartManager;

public class CartActivity extends AppCompatActivity implements CartAdapter.CartItemListener {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private TextView totalPriceView;
    private LinearLayout emptyState;
    private LinearLayout cartFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Shopping Cart");
        }

        recyclerView = findViewById(R.id.recycler_cart);
        totalPriceView = findViewById(R.id.cart_total_price);
        emptyState = findViewById(R.id.empty_cart_view);
        cartFooter = findViewById(R.id.cart_footer);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(this, CartManager.getInstance().getItems(), this);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btn_checkout).setOnClickListener(v -> {
            if (CartManager.getInstance().getItems().isEmpty()) {
                Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, CheckoutActivity.class));
            }
        });

        updateUI();
    }

    private void updateUI() {
        adapter.notifyDataSetChanged();
        totalPriceView.setText(String.format("$%.2f", CartManager.getInstance().getTotalPrice()));

        if (CartManager.getInstance().getItems().isEmpty()) {
            emptyState.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            cartFooter.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            cartFooter.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onIncrement(CartItem item) {
        CartManager.getInstance().incrementItem(item);
        updateUI();
    }

    @Override
    public void onDecrement(CartItem item) {
        CartManager.getInstance().decrementItem(item);
        updateUI();
    }

    @Override
    public void onRemove(CartItem item) {
        CartManager.getInstance().removeItem(item);
        updateUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}