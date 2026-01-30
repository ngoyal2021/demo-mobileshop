package com.example.shopmobile.util;

import androidx.annotation.VisibleForTesting;

import com.example.shopmobile.model.CartItem;
import com.example.shopmobile.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartItem> items;

    private CartManager() {
        items = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    /**
     * Test Seam: Allows tests to reset the singleton state.
     */
    @VisibleForTesting
    public static synchronized void resetInstance() {
        instance = null;
        instance = new CartManager();
    }

    public void addItem(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        items.add(new CartItem(product, 1));
    }

    public void incrementItem(CartItem item) {
        item.setQuantity(item.getQuantity() + 1);
    }

    public void decrementItem(CartItem item) {
        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
        }
    }

    public void removeItem(CartItem item) {
        items.remove(item);
    }

    public void clearCart() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public int getItemCount() {
        int count = 0;
        for (CartItem item : items) {
            count += item.getQuantity();
        }
        return count;
    }
}