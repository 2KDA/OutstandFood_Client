package android.outstandfood_client.view.screen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.outstandfood_client.R;
import android.outstandfood_client.databinding.ImgotherItemBinding;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolderDetail> {

    private Context context;

    private List<String> stringArrayList;

    @SuppressLint("NotifyDataSetChanged")
    public DetailAdapter(Context context, List<String> stringArrayList) {
        this.context = context;
        this.stringArrayList=stringArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderDetail onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImgotherItemBinding binding=ImgotherItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolderDetail(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDetail holder, int position) {
        Glide.with(context).load(stringArrayList.get(position)).placeholder(R.drawable.account).error(R.drawable.avartar).into(holder.binding.imgOther);
    }

    @Override
    public int getItemCount() {
        if (stringArrayList.size()==0){
            return 0;
        }
        return stringArrayList.size();
    }

    public static class ViewHolderDetail extends RecyclerView.ViewHolder {
          ImgotherItemBinding binding;
          public ViewHolderDetail(@NonNull ImgotherItemBinding itemView) {
              super(itemView.getRoot());
              this.binding=itemView;
          }
      }

}
