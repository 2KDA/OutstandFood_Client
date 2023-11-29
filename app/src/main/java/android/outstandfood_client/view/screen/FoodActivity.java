package android.outstandfood_client.view.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.outstandfood_client.R;
import android.outstandfood_client.databinding.ActivityFoodBinding;
import android.outstandfood_client.view.screen.adapter.ListProductAdapter;
import android.view.LayoutInflater;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {
     private ActivityFoodBinding binding;
     private ListProductAdapter listProductAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        listProductAdapter=new  ListProductAdapter();
        ArrayList<String> list=new ArrayList<>();
        list.add("asa");
        list.add("ádas");
        list.add("adda");
        listProductAdapter.setData(list);
        GridLayoutManager manager=new GridLayoutManager(this,2, RecyclerView.VERTICAL,false);
        binding.recyFood.setLayoutManager(manager);
        binding.recyFood.setAdapter(listProductAdapter);
    }
}