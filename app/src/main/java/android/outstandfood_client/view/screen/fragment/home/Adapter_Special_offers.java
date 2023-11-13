package android.outstandfood_client.view.screen.fragment.home;

import android.content.Context;
import android.outstandfood_client.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Special_offers extends RecyclerView.Adapter<Adapter_Special_offers.UserViewHolder>{

    private Context mContext;
    private ArrayList<Integer> lis_Banner;

    public Adapter_Special_offers(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<Integer> lis_Banner){
        this.lis_Banner = lis_Banner;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_special,parent,false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        int i_banner = lis_Banner.get(position);

        holder.img_home_item_special_banner.setImageResource(i_banner);

    }

    @Override
    public int getItemCount() {
        if (lis_Banner != null) return lis_Banner.size();
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_home_item_special_banner;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            img_home_item_special_banner = itemView.findViewById(R.id.home_item_special_banner);
        }
    }
}
