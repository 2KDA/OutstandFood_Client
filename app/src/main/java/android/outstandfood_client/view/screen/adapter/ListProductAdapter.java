package android.outstandfood_client.view.screen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.outstandfood_client.R;
import android.outstandfood_client.databinding.ItemProductBinding;
import android.outstandfood_client.interfaces.FoodInterface;
import android.outstandfood_client.models.Product;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.viewHolderCategory> implements Filterable {
    private ArrayList<Product> list;

    private ArrayList<Product> listOld;
    private Context mContext;
    private FoodInterface foodInterface;

    public ListProductAdapter(Context mContext, FoodInterface foodInterface) {
        this.mContext = mContext;
        this.foodInterface = foodInterface;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<Product> list) {
        this.list = list;
        this.listOld=list;
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
        Product product=list.get(position);
        Glide.with(mContext).load(product.getImage()).error(R.drawable.avartar).into(holder.binding.imFood);
//        holder.binding.tvPrice.setText("Số lượng: "+String.valueOf(product.getQuantity()));
        holder.binding.tvPriceNum.setText(product.getPrice()+" VNĐ");
        holder.binding.tvName.setText(product.getName());
        holder.binding.getRoot().setOnClickListener(view -> {
            foodInterface.DetailFood(product);
        });
        holder.binding.constraintAdd.setOnClickListener(view -> foodInterface.addFood(product));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String Search=charSequence.toString();
                if (Search.isEmpty()){
                    list=listOld;
                }else {
                    ArrayList<Product> foodModels=new ArrayList<>();
                    for (Product model:listOld) {
                        if (model.getName().toLowerCase().contains(Search.toLowerCase())){
                            foodModels.add(model);
                        }
                    }
                    list=foodModels;
                }
                FilterResults results =new FilterResults();
                results.values=list;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list= (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class viewHolderCategory extends RecyclerView.ViewHolder {
        ItemProductBinding binding;

        public viewHolderCategory(@NonNull ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}