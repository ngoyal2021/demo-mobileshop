package com.example.shopmobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopmobile.util.CartManager;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        // Clear the cart as the order is confirmed
        CartManager.getInstance().clearCart();

        Button btnHome = findViewById(R.id.btn_back_home);
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            // Clear the activity stack so the user cannot go back to the checkout/cart screens
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
    
    @Override
    public void onBackPressed() {
        // Override back button to behave same as "Back to Home"
        // to prevent going back to Checkout page
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}