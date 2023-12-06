package android.outstandfood_client.view.screen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.outstandfood_client.R;
import android.outstandfood_client.databinding.ItemCategoryBinding;
import android.outstandfood_client.databinding.ItemProductBinding;
import android.outstandfood_client.models.Product;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.viewHolderCategory> {
    private ArrayList<Product> list;
    private Context mContext;

    public ListProductAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<Product> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new viewHolderCategory(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderCategory holder, int position) {
        Product product=list.get(position);
        Glide.with(mContext).load("https://outstanfood-com.onrender.com/"+product.getImage()).error(R.drawable.avartar).into(holder.binding.imFood);
        holder.binding.tvPrice.setText("Số lượng: "+String.valueOf(product.getQuantity()));
        holder.binding.tvPriceNum.setText(product.getPrice()+" VNĐ");
        holder.binding.tvName.setText(product.getName());
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolderCategory extends RecyclerView.ViewHolder {
        ItemProductBinding binding;

        public viewHolderCategory(@NonNull ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
