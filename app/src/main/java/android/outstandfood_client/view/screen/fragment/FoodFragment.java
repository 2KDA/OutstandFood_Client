package android.outstandfood_client.view.screen.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.outstandfood_client.OutstandFragment;
import android.outstandfood_client.Utils;
import android.outstandfood_client.data.CartDatabase;
import android.outstandfood_client.data.CartModel;
import android.outstandfood_client.databinding.FragmentFoodBinding;
import android.outstandfood_client.interfaces.ApiService;
import android.outstandfood_client.interfaces.FoodInterface;
import android.outstandfood_client.models.HistoryModel;
import android.outstandfood_client.models.ListCategory;
import android.outstandfood_client.models.ListProduct;
import android.outstandfood_client.models.Product;
import android.outstandfood_client.models.Rating;
import android.outstandfood_client.models.User;
import android.outstandfood_client.view.screen.FoodActivity;
import android.outstandfood_client.view.screen.adapter.ListCateAdapter;
import android.outstandfood_client.view.screen.adapter.ListProductAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.outstandfood_client.R;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodFragment extends OutstandFragment implements FoodInterface {
    private ListCateAdapter listCateAdapter;
    private FragmentFoodBinding binding;
    private ListProductAdapter listProductAdapter;

    private ListProduct listProduct;

    public FoodFragment() {
        // Required empty public constructor
    }

    public static FoodFragment newInstance(String param1, String param2) {
        FoodFragment fragment = new FoodFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFoodBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        binding.progressCircular.setVisibility(View.VISIBLE);
        GridLayoutManager manager = new GridLayoutManager(requireActivity(), 2, RecyclerView.VERTICAL, false);
        binding.recyFood.setLayoutManager(manager);
        listProductAdapter = new ListProductAdapter(requireActivity(), this);
        ApiService.API_SERVICER.getProductList("TL001").enqueue(new Callback<ListProduct>() {
            @Override
            public void onResponse(Call<ListProduct> call, Response<ListProduct> response) {
                listProduct = response.body();
                Log.d("TAG", "onResponse: " + listProduct.getMsg());
                listProductAdapter.setData(listProduct.getProduct());
                binding.recyFood.setAdapter(listProductAdapter);
                binding.progressCircular.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ListProduct> call, Throwable t) {
                binding.progressCircular.setVisibility(View.GONE);
            }
        });
        binding.SearchFood.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                listProductAdapter.getFilter().filter(s);
//                if (listProductAdapter.getItemCount() == 0) {
//                   binding.tvResult.setVisibility(View.VISIBLE);
//                } else {
//                    binding.tvResult.setVisibility(View.GONE);
//                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listProductAdapter.getFilter().filter(s);
//                if (listProductAdapter.getItemCount() == 0) {
//                    binding.tvResult.setVisibility(View.VISIBLE);
//                } else {
//                    binding.tvResult.setVisibility(View.GONE);
//                }
                return false;
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

    @Override
    public void DetailFood(Product product) {
        Intent intent = new Intent(requireActivity(), FoodActivity.class);
        intent.putExtra("FOOD",product);
        startActivity(intent);
    }

    @Override
    public void addRating(User user) {

    }


    private void AddCart(Product product) {
        if (product.getQuantity()<=0){
            Utils.showCustomToast(requireActivity(),"Hết hàng");
        }
        CartModel model = null;
        model = CartDatabase.getInstance(requireActivity()).cartDao().findByID(product.get_id());
        if (model == null) {
            Log.d("TAG", "AddCart: " + product.get_id());
            CartModel cartModel = new CartModel(product.get_id(), product.getName(), product.getPrice(), 1, product.getImage());
            Log.d("TAG", "AddCart: " + cartModel);
            CartDatabase.getInstance(requireActivity()).cartDao().AddCart(cartModel);
            Utils.showCustomToast(requireActivity(),"Thanh cong");
        } else {
            model.setQuantityFood(model.getQuantityFood() + 1);
            CartDatabase.getInstance(requireActivity()).cartDao().UpdateCart(model);
            Utils.showCustomToast(requireActivity(),"Thanh cong");
        }

    }

}