package android.outstandfood_client.view.screen.adapter;

import android.content.Context;
import android.outstandfood_client.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends BaseAdapter {
    Context mContext;
    List<String> stringArrayList;
    int item;

    public DetailAdapter(Context mContext, List<String> stringArrayList, int item) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;
        this.item = item;
    }

    @Override
    public int getCount() {
        if (stringArrayList == null) {
            return 0;
        }
        return stringArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(item, null);
            ImageView imageView = convertView.findViewById(R.id.imgOther);
            Glide.with(mContext).load(stringArrayList.get(position)).placeholder(R.drawable.account).error(R.drawable.avartar).into(imageView);
        }

        return convertView;
    }
}
