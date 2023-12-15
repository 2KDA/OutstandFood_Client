package android.outstandfood_client.view.screen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.outstandfood_client.OutstandActivity;
import android.outstandfood_client.R;
import android.outstandfood_client.Utils;
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

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivity extends OutstandActivity {
    private ActivityFoodBinding binding;
    private ArrayList<Product> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        binding.imgBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    @SuppressLint("CheckResult")
    private void initView() {
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("FOOD");
        if (product==null){
            return;
        }
        Log.d("TAG5", "initView: "+product.getImage());
        Glide.with(this).load(product.getImage()).error(R.drawable.avartar).placeholder(R.drawable.avt_test).into(binding.imgBanner);
        binding.tvNameDeatail.setText(product.getName());
        binding.tvPriceDetail.setText(String.valueOf(product.getPrice()));
        binding.tvDescriptionD.setText(product.getDescribe());
        binding.btnAddcart.setOnClickListener(view -> AddCart(product));
    }


    private void AddCart(Product product) {
        CartModel model = null;
        model = CartDatabase.getInstance(this).cartDao().findByID(product.get_id());
        if (model == null) {
            Log.d("TAG", "AddCart: " + product.get_id());
            CartModel cartModel = new CartModel(product.get_id(), product.getName(), product.getPrice(), 1, product.getImage());
            Log.d("TAG", "AddCart: " + cartModel);
            CartDatabase.getInstance(this).cartDao().AddCart(cartModel);
            Utils.showCustomToast(this,"Thanh cong");
        } else {
            model.setQuantityFood(model.getQuantityFood() + 1);
            CartDatabase.getInstance(this).cartDao().UpdateCart(model);
            Utils.showCustomToast(this,"Thanh cong");
        }

    }
}