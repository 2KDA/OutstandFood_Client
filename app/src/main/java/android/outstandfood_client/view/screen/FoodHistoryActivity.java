//package android.outstandfood_client.view.screen;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.outstandfood_client.R;
//import android.outstandfood_client.databinding.ActivityFoodHistoryBinding;
//import android.outstandfood_client.interfaces.ApiService;
//import android.outstandfood_client.interfaces.FoodInterface;
//import android.outstandfood_client.interfaces.HistoryInter;
//import android.outstandfood_client.models.ListDetail;
//import android.outstandfood_client.models.ListProduct;
//import android.outstandfood_client.models.Product;
//import android.outstandfood_client.models.ProductOrdered;
//import android.outstandfood_client.view.screen.adapter.ListHistoryAdapter;
//import android.outstandfood_client.view.screen.adapter.ListProductAdapter;
//import android.util.Log;
//import android.view.View;
//
//import java.util.ArrayList;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class FoodHistoryActivity extends AppCompatActivity implements HistoryInter {
//    private ActivityFoodHistoryBinding binding;
//
//    private ListHistoryAdapter listProductAdapter;
//
//    ArrayList<ProductOrdered> list = new ArrayList<>();
//
//    private ListProduct listProduct;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityFoodHistoryBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//    }
//
//    private void initView() {
//        Intent intent=getIntent();
//        ListDetail detail= (ListDetail) intent.getExtras().get("list");
//        binding.progressCircular.setVisibility(View.VISIBLE);
//        GridLayoutManager manager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
//        binding.recyFood.setLayoutManager(manager);
//        listProductAdapter = new ListHistoryAdapter(this, this);
//        ApiService.API_SERVICER.getProductList("TL001").enqueue(new Callback<ListProduct>() {
//            @Override
//            public void onResponse(Call<ListProduct> call, Response<ListProduct> response) {
//                listProduct = response.body();
//                Log.d("TAG", "onResponse: " + listProduct.getMsg());
//                for (int i = 0; i < listProduct.getProduct().size(); i++) {
//                    ProductOrdered productOrdered = new ProductOrdered(listProduct.getProduct().get(i).get_id(), listProduct.getProduct().get(i).getName(),
//                            listProduct.getProduct().get(i).getPrice(), listProduct.getProduct().get(i).getImage(),
//                            listProduct.getProduct().get(i).getDescribe(), listProduct.getProduct().get(i).get_v(),
//                            listProduct.getProduct().get(i).getQuantity(), listProduct.getProduct().get(i).getId_category().get_id(),
//                            listProduct.getProduct().get(i).getCategory_name());
//                    list.add(productOrdered);
//                }
//
//                listProductAdapter.setData(detail);
//                binding.recyFood.setAdapter(listProductAdapter);
//                binding.progressCircular.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onFailure(Call<ListProduct> call, Throwable t) {
//                binding.progressCircular.setVisibility(View.GONE);
//            }
//        });
//    }
//
//
//    @Override
//    public void FoodHistory(ProductOrdered ordered) {
//
//    }
//}