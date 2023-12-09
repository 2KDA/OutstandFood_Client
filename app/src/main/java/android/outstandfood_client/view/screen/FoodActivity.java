package android.outstandfood_client.view.screen;

import android.content.Intent;
import android.os.Bundle;
import android.outstandfood_client.data.CartDatabase;
import android.outstandfood_client.data.CartModel;
import android.outstandfood_client.databinding.ActivityFoodBinding;
import android.outstandfood_client.interfaces.ApiService;
import android.outstandfood_client.interfaces.FoodInterface;
import android.outstandfood_client.models.ListProduct;
import android.outstandfood_client.models.Product;
import android.outstandfood_client.view.screen.adapter.ListProductAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivity extends AppCompatActivity implements FoodInterface {
    private ActivityFoodBinding binding;
    private ListProductAdapter listProductAdapter;

    private ListProduct listProduct;
    private ArrayList<Product> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        binding.imBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void initView() {
        list = new ArrayList<>();
        binding.progressCircular.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("intent");
        assert bundle != null;
        String id = bundle.getString("idCate");
        String name = bundle.getString("name");
        binding.tvCate.setText(name);
        GridLayoutManager manager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        binding.recyFood.setLayoutManager(manager);
        listProductAdapter = new ListProductAdapter(this, this);
        ApiService.API_SERVICER.getListProduct(id).enqueue(new Callback<ListProduct>() {
            @Override
            public void onResponse(Call<ListProduct> call, Response<ListProduct> response) {
                Log.d("TAG", "onResponse: " + id);
                listProduct = response.body();
                for (int i = 0; i < listProduct.getProduct().size(); i++) {
                    if (listProduct.getProduct().get(i).getId_category().get_id().equals(id)) {
                        list.add(listProduct.getProduct().get(i));
                    }
                }
                assert listProduct != null;
                Log.d("TAG", "onResponse: " + listProduct.getMsg());
                listProductAdapter.setData(list);
                binding.recyFood.setAdapter(listProductAdapter);
                binding.progressCircular.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ListProduct> call, Throwable t) {

            }
        });

    }

    @Override
    public void showFood(String id, String name) {

    }

    @Override
    public void addFood(Product product) {
        AddCart(product);
    }

    private void AddCart(Product product) {
        CartModel model = null;
        model = CartDatabase.getInstance(this).cartDao().findByID(product.get_id());
        if (model == null) {
            Log.d("TAG", "AddCart: "+product.get_id());
            CartModel cartModel = new CartModel(product.get_id(), product.getName(), product.getPrice(), 1, product.getImage());
            Log.d("TAG", "AddCart: "+cartModel);
            CartDatabase.getInstance(this).cartDao().AddCart(cartModel);
            Toast.makeText(this, "Thanh cong", Toast.LENGTH_SHORT).show();
        } else {
            model.setQuantityFood(model.getQuantityFood() + 1);
            CartDatabase.getInstance(this).cartDao().UpdateCart(model);
            Toast.makeText(this, "Thanh cong", Toast.LENGTH_SHORT).show();
        }

    }
}