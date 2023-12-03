package android.outstandfood_client.view.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.outstandfood_client.R;
import android.outstandfood_client.databinding.ActivityFoodBinding;
import android.outstandfood_client.interfaces.ApiService;
import android.outstandfood_client.models.ListProduct;
import android.outstandfood_client.models.Product;
import android.outstandfood_client.view.screen.adapter.ListProductAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivity extends AppCompatActivity {
     private ActivityFoodBinding binding;
     private ListProductAdapter listProductAdapter;

     private ListProduct listProduct;
     private ArrayList<Product> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        binding.imBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void initView() {
        list=new ArrayList<>();
        binding.progressCircular.setVisibility(View.VISIBLE);
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("intent");
        assert bundle != null;
        String id= bundle.getString("idCate");
        String name= bundle.getString("name");
        binding.tvCate.setText(name);
        GridLayoutManager manager=new GridLayoutManager(this,2, RecyclerView.VERTICAL,false);
        binding.recyFood.setLayoutManager(manager);
        listProductAdapter=new  ListProductAdapter(this);
        ApiService.API_SERVICER.getListProduct(id).enqueue(new Callback<ListProduct>() {
            @Override
            public void onResponse(Call<ListProduct> call, Response<ListProduct> response) {
                Log.d("TAG", "onResponse: "+id);
                listProduct=response.body();
                for (int i=0; i <listProduct.getProduct().size();i++){
                    if (listProduct.getProduct().get(i).getId_category().get_id().equals(id)){
                        list.add(listProduct.getProduct().get(i));
                    }
                }
                assert listProduct != null;
                Log.d("TAG", "onResponse: "+listProduct.getMsg());
                listProductAdapter.setData(list);
                binding.recyFood.setAdapter(listProductAdapter);
                binding.progressCircular.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ListProduct> call, Throwable t) {

            }
        });

    }
}