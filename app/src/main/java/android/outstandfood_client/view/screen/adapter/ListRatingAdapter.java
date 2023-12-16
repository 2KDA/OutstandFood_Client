package android.outstandfood_client.view.screen.adapter;

import android.outstandfood_client.R;
import android.outstandfood_client.models.Rating;
import android.outstandfood_client.models.User;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListRatingAdapter extends RecyclerView.Adapter<ListRatingAdapter.ListRatingViewHolder> {

    private List<Rating> listRating;

    public ListRatingAdapter(List<Rating> listRating) {
        this.listRating = listRating;
    }

    @NonNull
    @Override
    public ListRatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rating, parent, false);
        return new ListRatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRatingViewHolder holder, int position) {
        Rating objRating = listRating.get(position);
        if (objRating == null){
            return;
        }

        holder.tv_user.setText(objRating.getUser_name());
        holder.tv_rating.setText(objRating.getRating());
    }

    @Override
    public int getItemCount() {
        if (listRating != null){
            return listRating.size();
        }
        return 0;
    }

    public class ListRatingViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_user, tv_rating;
        public ListRatingViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_user = itemView.findViewById(R.id.tv_itemUserComment);
            tv_rating = itemView.findViewById(R.id.tv_itemContentComment);
        }
    }
}
