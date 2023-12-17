package android.outstandfood_client.view.screen.adapter;

import android.annotation.SuppressLint;
import android.outstandfood_client.databinding.ItemHistoryBinding;
import android.outstandfood_client.models.HistoryModel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private ArrayList<HistoryModel> list;

    private HisAdapter historyInter;
    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());

    public HistoryAdapter(HisAdapter historyInter) {
        this.historyInter = historyInter;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<HistoryModel> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoryBinding binding=ItemHistoryBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new HistoryViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryModel historyModel=list.get(position);
        if (historyModel==null){
            return;
        }
        Date date = null;
        try {
            date = inputFormat.parse(historyModel.getOrdered().getDate());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
            String outputDateString = outputFormat.format(date);
            holder.binding.tvDateO.setText(outputDateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        holder.binding.tvMailU.setText(historyModel.getOrdered().getId_user().getUserEmail());
        holder.binding.tvPhone.setText(historyModel.getOrdered().getPhone());
        holder.binding.tvNameU.setText(historyModel.getOrdered().getId_user().getName());
        holder.binding.tvAdressU.setText(historyModel.getOrdered().getAddress());
        holder.binding.tvCode.setText(historyModel.getOrdered().get_id());
        if (historyModel.getListDetail().size()>=2){
            StringBuilder list= new StringBuilder();
            for (int i=0;i<historyModel.getListDetail().size();i++){
                list.append(historyModel.getListDetail().get(i).getId_product().getName());
                list.append(",");
            }
            holder.binding.tvMenu.setText(list);
        }else {
            holder.binding.tvMenu.setText(historyModel.getListDetail().get(0).getId_product().getName());
        }
        holder.binding.tvPT.setText(historyModel.getOrdered().getDelivery_status());
     holder.binding.SumMoney.setText(historyModel.getOrdered().getTotal_price()+" VNĐ");
     holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             historyInter.hisAdapter(historyModel.getListDetail().get(position));
         }
     });
    }

    @Override
    public int getItemCount() {
        if (list==null){
            return 0;
        }
        return list.size();
    }


    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private ItemHistoryBinding binding;
        public HistoryViewHolder(@NonNull ItemHistoryBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }
}
