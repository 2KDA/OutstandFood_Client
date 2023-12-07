package android.outstandfood_client.view.screen.adapter;

import android.annotation.SuppressLint;
import android.outstandfood_client.databinding.ItemCartBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    //list test
    private ArrayList<String> cart;

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<String> cart){
        this.cart=cart;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding binding=ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ItemCartBinding binding;
        public CartViewHolder(@NonNull ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
