package android.outstandfood_client.view.screen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.outstandfood_client.R;
import android.outstandfood_client.data.CartModel;
import android.outstandfood_client.databinding.ItemCartBinding;
import android.outstandfood_client.databinding.ItemNotificationBinding;
import android.outstandfood_client.models.AddressModel;
import android.outstandfood_client.models.Notification;
import android.outstandfood_client.models.Product;
import android.outstandfood_client.view.screen.fragment.HistoryFragment;
import android.outstandfood_client.view.screen.home_action_menu.Home_Screen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{


    private ArrayList<Notification> notificationList;
    private Context context;
    private OnNotificationClickListener clickListener;

    public void setOnNotificationClickListener(OnNotificationClickListener listener) {
        this.clickListener = listener;
    }

    public NotificationAdapter(ArrayList<Notification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<Notification> list) {
        this.notificationList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotificationBinding binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NotificationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        if (notification == null) {
            return;
        }
        holder.binding.tvMessage.setText(notification.getMessage());
        holder.binding.tvTime.setText(notification.getCreatedAt());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, Home_Screen.class));
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clickListener.onNotificationLongClick(notification.get_id());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        ItemNotificationBinding binding;
        public NotificationViewHolder(@NonNull ItemNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public interface OnNotificationClickListener {
        void onNotificationLongClick(String id);
    }
}
