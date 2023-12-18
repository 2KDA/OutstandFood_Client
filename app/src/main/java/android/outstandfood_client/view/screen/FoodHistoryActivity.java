package android.outstandfood_client.view.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.outstandfood_client.R;
import android.outstandfood_client.Utils;
import android.outstandfood_client.data.CartDatabase;
import android.outstandfood_client.data.CartModel;
import android.outstandfood_client.databinding.ActivityFoodHistoryBinding;
import android.outstandfood_client.interfaces.ApiService;
import android.outstandfood_client.interfaces.FoodInterface;
import android.outstandfood_client.interfaces.HistoryInter;
import android.outstandfood_client.models.ListDetail;
import android.outstandfood_client.models.ListProduct;
import android.outstandfood_client.models.Product;
import android.outstandfood_client.models.ProductOrdered;
import android.outstandfood_client.view.FoodActivityDetail;
import android.outstandfood_client.view.screen.adapter.ListHistoryAdapter;
import android.outstandfood_client.view.screen.adapter.ListProductAdapter;
import android.util.Log;
import android.view.View;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodHistoryActivity extends AppCompatActivity implements HistoryInter {
    private ActivityFoodHistoryBinding binding;

    private ListHistoryAdapter listProductAdapter;

    ArrayList<ProductOrdered> list = new ArrayList<>();

    private ListProduct listProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        ArrayList<ListDetail> detail= Hawk.get("list");
        binding.progressCircular.setVisibility(View.VISIBLE);
        GridLayoutManager manager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        binding.recyFood.setLayoutManager(manager);
        listProductAdapter = new ListHistoryAdapter(this, this);
        listProductAdapter.setData(detail);
        binding.recyFood.setAdapter(listProductAdapter);
        binding.progressCircular.setVisibility(View.GONE);
        binding.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    @Override
    public void FoodHistory(ProductOrdered ordered) {
        CartModel model = null;
        model = CartDatabase.getInstance(this).cartDao().findByID(ordered.get_id());
        if (model == null) {
            Log.d("TAG", "AddCart: " + ordered.get_id());
            CartModel cartModel = new CartModel(ordered.get_id(), ordered.getName(), ordered.getPrice(), 1, ordered.getImage());
            Log.d("TAG", "AddCart: " + cartModel);
            CartDatabase.getInstance(this).cartDao().AddCart(cartModel);
            Utils.showCustomToast(this,"Thanh cong");
        } else {
            model.setQuantityFood(model.getQuantityFood() + 1);
            CartDatabase.getInstance(this).cartDao().UpdateCart(model);
            Utils.showCustomToast(this,"Thanh cong");
        }

    }

    @Override
    public void IntenDetail(ProductOrdered ordered) {
        Intent intent = new Intent(this, FoodActivityDetail.class);
        intent.putExtra("FOOD",ordered);
        startActivity(intent);
    }
}