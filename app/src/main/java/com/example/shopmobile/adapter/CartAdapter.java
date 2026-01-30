package com.example.shopmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopmobile.R;
import com.example.shopmobile.model.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> items;
    private CartItemListener listener;

    public interface CartItemListener {
        void onIncrement(CartItem item);
        void onDecrement(CartItem item);
        void onRemove(CartItem item);
    }

    public CartAdapter(Context context, List<CartItem> items, CartItemListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    public void updateItems(List<CartItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = items.get(position);
        String productName = item.getProduct().getName();

        holder.title.setText(productName);
        holder.category.setText(item.getProduct().getCategory());

        // Display Unit Price as per requirement
        holder.price.setText(String.format("$%.2f", item.getProduct().getPrice()));

        holder.quantity.setText(String.valueOf(item.getQuantity()));

        // Accessibility improvements
        holder.btnIncrease.setContentDescription("Increase quantity of " + productName);
        holder.btnDecrease.setContentDescription("Decrease quantity of " + productName);
        holder.btnRemove.setContentDescription("Remove " + productName + " from cart");

        // UI Logic: Disable decrement button if quantity is 1
        boolean canDecrement = item.getQuantity() > 1;
        holder.btnDecrease.setEnabled(canDecrement);
        holder.btnDecrease.setAlpha(canDecrement ? 1.0f : 0.3f);

        Glide.with(context)
                .load(item.getProduct().getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(holder.image);

        holder.btnIncrease.setOnClickListener(v -> listener.onIncrement(item));
        holder.btnDecrease.setOnClickListener(v -> listener.onDecrement(item));
        holder.btnRemove.setOnClickListener(v -> listener.onRemove(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, category, price, quantity;
        Button btnIncrease, btnDecrease;
        ImageButton btnRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cart_item_image);
            title = itemView.findViewById(R.id.cart_item_title);
            category = itemView.findViewById(R.id.cart_item_category);
            price = itemView.findViewById(R.id.cart_item_price);
            quantity = itemView.findViewById(R.id.cart_item_quantity);
            btnIncrease = itemView.findViewById(R.id.btn_increase);
            btnDecrease = itemView.findViewById(R.id.btn_decrease);
            btnRemove = itemView.findViewById(R.id.btn_remove);
        }
    }
}