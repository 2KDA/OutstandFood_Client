package android.outstandfood_client.view.screen.adapter;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.outstandfood_client.R;
import android.outstandfood_client.databinding.ItemCategoryBinding;
import android.outstandfood_client.databinding.ItemProductBinding;
import android.outstandfood_client.interfaces.FoodInterface;
import android.outstandfood_client.models.Product;
import android.outstandfood_client.object.CommonActivity;
import android.outstandfood_client.object.SharedPrefsManager;
import android.outstandfood_client.view.screen.Login_screen;
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
    private FoodInterface foodInterface;

    public ListProductAdapter(Context mContext, FoodInterface foodInterface) {
        this.mContext = mContext;
        this.foodInterface = foodInterface;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolderCategory holder, int position) {
        Product product = list.get(position);
        Glide.with(mContext).load(product.getImage()).error(R.drawable.avartar).into(holder.binding.imFood);
//        holder.binding.tvPrice.setText("Số lượng: "+String.valueOf(product.getQuantity()));
        holder.binding.tvPriceNum.setText(product.getPrice() + " VNĐ");
        holder.binding.tvName.setText(product.getName());
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SharedPrefsManager.getUser(mContext) != null) {
                    foodInterface.addFood(product);
                } else {
                    CommonActivity.createDialog((Activity) mContext,
                            "Bạn phải đăng nhập mới có thể sử dụng chức năng của cửa hàng !",
                            "Outstand'Food", "Hủy", "Đồng ý",
                            null,
                            v -> {
                                Intent intent = new Intent(mContext, Login_screen.class);
                                mContext.startActivity(intent);
                            }
                    ).show();
                }
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
