package android.outstandfood_client.view.screen.adapter;

import android.annotation.SuppressLint;
import android.outstandfood_client.databinding.ItemCategoryBinding;
import android.outstandfood_client.interfaces.FoodInterface;
import android.outstandfood_client.models.Category;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListCateAdapter extends RecyclerView.Adapter<ListCateAdapter.viewHolderCategory> {
    private ArrayList<Category> list;
    private FoodInterface foodInterface;

    public ListCateAdapter(FoodInterface foodInterface) {
        this.foodInterface = foodInterface;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<Category> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new viewHolderCategory(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderCategory holder, int position) {
        Category category=list.get(position);
        if (category==null){
            return;
        }
        holder.binding.tvCate.setText(category.getName());
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             foodInterface.showFood(category.get_id(),category.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolderCategory extends RecyclerView.ViewHolder {
        ItemCategoryBinding binding;

        public viewHolderCategory(@NonNull ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
