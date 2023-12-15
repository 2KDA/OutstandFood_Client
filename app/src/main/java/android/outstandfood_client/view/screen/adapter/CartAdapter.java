package android.outstandfood_client.view.screen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.outstandfood_client.R;
import android.outstandfood_client.data.CartModel;
import android.outstandfood_client.databinding.ItemCartBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    //list test
    private ArrayList<CartModel> cart;
    private Context mContext;
    private InterCart interCart;

    public interface InterCart {
        void UpdateCart(CartModel cartModel);

        void RemoveCart(CartModel cartModel);

        void DeleteCart(CartModel cartModel);
    }

    public CartAdapter(Context mContext, InterCart interCart) {
        this.mContext = mContext;
        this.interCart = interCart;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<CartModel> cart) {
        this.cart = cart;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding binding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartModel cartModel = cart.get(position);
        if (cartModel == null) {
            return;
        }
        holder.binding.tvName.setText(cartModel.getNameFood());
        holder.binding.tvPrice.setText(String.valueOf(cartModel.getPriceFood() + " VNĐ"));
        holder.binding.tvSL.setText(String.valueOf(cartModel.getQuantityFood()));
        Glide.with(mContext).load(cartModel.getImgFood()).error(R.drawable.avartar).into(holder.binding.imFood);
        holder.binding.tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            interCart.RemoveCart(cartModel);
            }
        });
        holder.binding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interCart.UpdateCart(cartModel);
            }
        });
        holder.binding.imDeleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interCart.DeleteCart(cartModel);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ItemCartBinding binding;

        public CartViewHolder(@NonNull ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
