package android.outstandfood_client.view.screen.adapter;

import android.annotation.SuppressLint;
import android.outstandfood_client.databinding.ItemCategoryBinding;
import android.outstandfood_client.databinding.ItemProductBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.viewHolderCategory> {
    private ArrayList<String> list;

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<String> list) {
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
