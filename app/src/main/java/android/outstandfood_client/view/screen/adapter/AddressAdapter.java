package android.outstandfood_client.view.screen.adapter;

import android.content.Context;
import android.outstandfood_client.R;
import android.outstandfood_client.models.AddressModel;
import android.outstandfood_client.models.User;
import android.outstandfood_client.object.SharedPrefsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private List<AddressModel> addressList;
    private OnAddressClickListener listener;
    private Context context;

    public AddressAdapter(Context context, List<AddressModel> addressList, OnAddressClickListener listener) {
        this.context = context;
        this.addressList = addressList;
        this.listener = listener;
    }
    public void updateAddresstList(List<AddressModel> newAddresss) {
        addressList.clear();
        addressList.addAll(newAddresss);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address, parent, false);
        return new AddressViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        AddressModel address = addressList.get(position);
        holder.itemView.setOnClickListener(v -> listener.onAddressClick(address));

        User savedUser = SharedPrefsManager.getUser(context);

        holder.txtAddress.setText(address.getAddress());
        holder.txtPhone.setText(address.getPhone());
        if (address.getUserId().equals(savedUser.get_id())){
            holder.txtName.setText(savedUser.getName());
        }
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {
        // Các view hiển thị thông tin địa chỉ
        private TextView txtAddress;
        private TextView txtPhone,txtName;

        public AddressViewHolder(View itemView) {
            super(itemView);
            // Ánh xạ các view từ item layout
            txtAddress = itemView.findViewById(R.id.itemaddress);
            txtPhone = itemView.findViewById(R.id.itemphone);
            txtName = itemView.findViewById(R.id.itemtxtname);
        }
    }
    public interface OnAddressClickListener {
        void onAddressClick(AddressModel address);
    }
}
