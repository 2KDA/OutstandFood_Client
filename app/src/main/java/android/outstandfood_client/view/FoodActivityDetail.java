package android.outstandfood_client.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.outstandfood_client.OutstandActivity;
import android.outstandfood_client.R;
import android.outstandfood_client.Utils;
import android.outstandfood_client.data.CartDatabase;
import android.outstandfood_client.data.CartModel;
import android.outstandfood_client.databinding.ActivityFoodBinding;
import android.outstandfood_client.databinding.ActivityFoodDetailBinding;
import android.outstandfood_client.models.Product;
import android.outstandfood_client.models.ProductOrdered;
import android.outstandfood_client.view.screen.adapter.DetailAdapter;
import android.outstandfood_client.view.screen.adapter.DetailAdapter2;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class FoodActivityDetail extends OutstandActivity {
    private ActivityFoodDetailBinding binding;
    private DetailAdapter2 detailAdapter;
    ProductOrdered product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        getDataImage();
        binding.imgBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    @SuppressLint("CheckResult")
    private void initView() {
        Intent intent = getIntent();
        product = (ProductOrdered) intent.getSerializableExtra("FOOD");
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
    private void getDataImage(){
        GridLayoutManager manager=new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        binding.RecyImg.setLayoutManager(manager);
        detailAdapter=new DetailAdapter2(this,product.getImageDetail());
        binding.RecyImg.setAdapter(detailAdapter);
    }

    private void AddCart(ProductOrdered product) {
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