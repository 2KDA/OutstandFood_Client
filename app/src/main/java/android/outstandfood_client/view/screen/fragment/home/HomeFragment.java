package android.outstandfood_client.view.screen.fragment.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.outstandfood_client.interfaces.ApiService;
import android.outstandfood_client.models.ListProduct;
import android.outstandfood_client.models.Product;
import android.outstandfood_client.view.screen.adapter.ListProductAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.outstandfood_client.R;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private ArrayList<Product> list;
    private ListProduct listProduct;


    private EditText edt_home_ActionMenu_home_Craving;
    private ImageView img_home_ActionMenu_home_MyCart;
    private TextView tv_home_ActionMenu_homeSpecial_seeAll;
    private TextView tv_home_ActionMenu_homeRecommended_seeAll;

    // Loai do an
//    private ArrayList<Loai_food> list_FoodType = new ArrayList<>();
//    private Adapter_Foodtype adapter_FoodType;
//    private GridView gridView_home_ActionMenu_home_FoodType;

    // Do an giam gia
    private ArrayList<Food> lis_food = new ArrayList<>();
//    private Adapter_Discount adapter_discount;
//    private RecyclerView recyclerView_home_ActionMenu_home_Discout;

    // Do an vip pro
   private Adapter_Recommended adapter_recommended;
    private RecyclerView recyclerView_home_ActionMenu_home_Recommended;

    private ArrayList<Integer> lis_bannerSale = new ArrayList<>();
    private Adapter_Special_offers adapter_special_offers;
    private RecyclerView recyclerView_home_ActionMenu_Special_banner;


    public void AnhXa(View view){
//        edt_home_ActionMenu_home_Craving = view.findViewById(R.id.home_ActionMenu_home_Craving);
        img_home_ActionMenu_home_MyCart = view.findViewById(R.id.home_ActionMenu_home_MyCart);
//        tv_home_ActionMenu_homeRecommended_seeAll = view.findViewById(R.id.home_ActionMenu_homeRecommended_seeAll);

//        gridView_home_ActionMenu_home_FoodType = view.findViewById(R.id.home_ActionMenu_home_FoodType);
//        recyclerView_home_ActionMenu_home_Discout = view.findViewById(R.id.home_ActionMenu_home_Discount);
        recyclerView_home_ActionMenu_home_Recommended = view.findViewById(R.id.home_ActionMenu_home_Recommended);
        recyclerView_home_ActionMenu_Special_banner = view.findViewById(R.id.home_ActionMenu_Special_banner);
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AnhXa(view);

        //Special-Offers
        adapter_special_offers = new Adapter_Special_offers(getContext());
        adapter_special_offers.setData(lis_bannerSale);

        LinearLayoutManager manager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView_home_ActionMenu_Special_banner.setLayoutManager(manager2);
        recyclerView_home_ActionMenu_Special_banner.setAdapter(adapter_special_offers);

        lis_bannerSale.add(R.drawable.banner);
//        lis_bannerSale.add(R.drawable.banner_one);
//        lis_bannerSale.add(R.drawable.banner)
//        lis_bannerSale.add(R.drawable.banner);
//        lis_bannerSale.add(R.drawable.banner_one);

        //Recommended Food
        adapter_recommended = new Adapter_Recommended(getContext());


        initData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void initData() {
        list = new ArrayList<>();
        ApiService.API_SERVICER.getListProduct().enqueue(new Callback<ListProduct>() {
            @Override
            public void onResponse(Call<ListProduct> call, Response<ListProduct> response) {
                Log.d("TAG", "onResponse: " +response.body());
                listProduct = response.body();
                for (int i = 0; i < listProduct.getProduct().size(); i++) {
                        list.add(listProduct.getProduct().get(i));
                }
                assert listProduct != null;

                for(int i=0;i<3;i++){
                    Product product = list.get(i);
                    lis_food.add(new Food(product.getImage(),product.getName(),1.8,4.8,1,product.getPrice(),2.00));
                }
                adapter_recommended.setData(lis_food);

                LinearLayoutManager manager1 = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                recyclerView_home_ActionMenu_home_Recommended.setLayoutManager(manager1);
                recyclerView_home_ActionMenu_home_Recommended.setAdapter(adapter_recommended);
            }

            @Override
            public void onFailure(Call<ListProduct> call, Throwable t) {

            }
        });

    }
}