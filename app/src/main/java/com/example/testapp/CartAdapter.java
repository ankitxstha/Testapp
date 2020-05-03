package com.example.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.RecyclerViewAdapter> {
    private Context context;
    private List<Cart> carts;
    private ItemClickListener itemClickListener;

    public CartAdapter(Context context, List<Cart> carts, ItemClickListener itemClickListener) {
        this.context = context;
        this.carts = carts;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        Cart cart = carts.get(position);

        holder.title_cart.setText(cart.getProductName());
        holder.price_cart.setText(cart.getProductPrice());


    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title_cart, price_cart;
        CardView card_item_cart;
        ItemClickListener itemClickListener;

        public RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            title_cart = itemView.findViewById(R.id.title_cart);
            price_cart = itemView.findViewById(R.id.price_cart);
            card_item_cart = itemView.findViewById(R.id.card_item_cart);

            this.itemClickListener = itemClickListener;
            card_item_cart.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }
}
