package android.outstandfood_client.view.screen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.outstandfood_client.R;
import android.outstandfood_client.databinding.ItemProductBinding;
import android.outstandfood_client.interfaces.FoodInterface;
import android.outstandfood_client.interfaces.HistoryInter;
import android.outstandfood_client.models.ListDetail;
import android.outstandfood_client.models.Product;
import android.outstandfood_client.models.ProductOrdered;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ListHistoryAdapter extends RecyclerView.Adapter<ListHistoryAdapter.viewHolderCategory> {
    private ArrayList<ListDetail> list;
    private Context mContext;
    private HistoryInter foodInterface;

    public ListHistoryAdapter(Context mContext, HistoryInter foodInterface) {
        this.mContext = mContext;
        this.foodInterface = foodInterface;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<ListDetail> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new viewHolderCategory(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolderCategory holder, int position) {
        ListDetail product=list.get(position);
        Glide.with(mContext).load(product.getId_product().getImage()).error(R.drawable.avartar).into(holder.binding.imFood);
//        holder.binding.tvPrice.setText("Số lượng: "+String.valueOf(product.getQuantity()));
        holder.binding.tvPriceNum.setText(product.getPrice()+" VNĐ");
        holder.binding.tvName.setText(product.getId_product().getName());
        holder.binding.constraintAdd.setOnClickListener(view -> foodInterface.FoodHistory(product.getId_product()));
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodInterface.IntenDetail(product.getId_product());
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