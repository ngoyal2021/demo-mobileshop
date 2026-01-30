package com.example.shopmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopmobile.util.CartManager;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Checkout");
        }

        setupViews();
    }

    private void setupViews() {
        TextView itemsCountView = findViewById(R.id.summary_items_count);
        TextView subtotalView = findViewById(R.id.summary_subtotal);
        TextView totalView = findViewById(R.id.summary_total);
        Button confirmButton = findViewById(R.id.btn_confirm_order);

        CartManager cart = CartManager.getInstance();
        double total = cart.getTotalPrice();
        int count = cart.getItemCount();

        itemsCountView.setText(String.format("Items (%d)", count));
        subtotalView.setText(String.format("$%.2f", total));
        totalView.setText(String.format("$%.2f", total));

        confirmButton.setOnClickListener(v -> {
            // In a real app, we would process payment here.
            // For now, we assume success.
            Intent intent = new Intent(this, ConfirmationActivity.class);
            startActivity(intent);
            finish();
        });
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