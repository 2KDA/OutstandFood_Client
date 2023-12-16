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
import android.outstandfood_client.interfaceApi.ApiRating;
import android.outstandfood_client.interfaces.ApiService;
import android.outstandfood_client.interfaces.FoodInterface;
import android.outstandfood_client.models.ListProduct;
import android.outstandfood_client.models.Product;
import android.outstandfood_client.models.Rating;
import android.outstandfood_client.models.User;
import android.outstandfood_client.view.screen.adapter.DetailAdapter;
import android.outstandfood_client.view.screen.adapter.ListProductAdapter;
import android.outstandfood_client.view.screen.adapter.ListRatingAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivity extends OutstandActivity {
    private ActivityFoodBinding binding;
    private ArrayList<Product> list;
    List<Rating> listRating;

    List<Rating> list1;
    private DetailAdapter detailAdapter;
    Product product;


    private ListRatingAdapter listRatingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
//        getDataImage();
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

        ApiRating.apiRating.getListRating().enqueue(new Callback<List<Rating>>() {
            @Override
            public void onResponse(Call<List<Rating>> call, Response<List<Rating>> response) {
                list1 = response.body();

                for (Rating objRating : list1){
                    if (objRating.getProduct_name().equals(product.getName())){
                        listRatingAdapter.setData(list1);
                        Log.d("aaaaaa", "OBJRating: " + objRating.getProduct_name() + "Product: " + product.getName());
                    }
                }
                Log.d("aaaaaaa", "onResponse: " + list1);
            }

            @Override
            public void onFailure(Call<List<Rating>> call, Throwable t) {
                Log.d("aaaaaa", "onFailure: " + t);
            }
        });

//        getListRating();

        listRatingAdapter = new ListRatingAdapter(listRating);

        binding.rvRating.setAdapter(listRatingAdapter);
//        binding.btnAddRating.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getBaseContext(), AddRatingActivity.class);
//                startActivity(i);
//            }
//        });

    }
    private void getDataImage(){
        detailAdapter=new DetailAdapter(this,product.getImageDetail(),R.layout.imgother_item);
        binding.RecyImg.setAdapter(detailAdapter);
    }


    void getListRating(){
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("FOOD");

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