package android.outstandfood_client.view.screen.adapter;

import android.content.Context;
import android.outstandfood_client.R;
import android.outstandfood_client.models.Product;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SlideAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> foodModelArrayList;

    public SlideAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(ArrayList<String> foodModelArrayList){
        this.foodModelArrayList=foodModelArrayList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if (foodModelArrayList==null){
            return 0;
        }
        return foodModelArrayList.size();
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.slide_item,container,false);
        ImageView imageView =view.findViewById(R.id.imgsilde);
        if (foodModelArrayList!=null){
            Glide.with(mContext).load(foodModelArrayList.get(position)).placeholder(R.drawable.account).error(R.drawable.avartar).into(imageView);
        }
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
