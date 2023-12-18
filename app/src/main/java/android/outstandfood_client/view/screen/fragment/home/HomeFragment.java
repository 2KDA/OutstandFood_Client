package android.outstandfood_client.view.screen.fragment.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.outstandfood_client.interfaces.ApiService;
import android.outstandfood_client.models.ListProduct;
import android.outstandfood_client.models.Product;
import android.outstandfood_client.view.Introducts;
import android.outstandfood_client.view.screen.Notification_screen;
import android.outstandfood_client.view.screen.adapter.ListProductAdapter;
import android.outstandfood_client.view.screen.adapter.SlideAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.outstandfood_client.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private ArrayList<Product> list;
    private ListProduct listProduct;
    ViewPager VpSlide;
    CircleIndicator CrSlide;
    private Button btnandress_home;
    SlideAdapter slideAdapter;
    Timer mTimer;
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
    private ArrayList<String> imageFood;
    private ArrayList<Integer> lis_bannerSale = new ArrayList<>();
    private Adapter_Special_offers adapter_special_offers;
    private RecyclerView recyclerView_home_ActionMenu_Special_banner;

    public void AnhXa(View view) {
//        edt_home_ActionMenu_home_Craving = view.findViewById(R.id.home_ActionMenu_home_Craving);
        img_home_ActionMenu_home_MyCart = view.findViewById(R.id.home_ActionMenu_home_MyCart);
//        tv_home_ActionMenu_homeRecommended_seeAll = view.findViewById(R.id.home_ActionMenu_homeRecommended_seeAll);
        VpSlide = view.findViewById(R.id.Vp_Home);
        CrSlide = view.findViewById(R.id.CrSlide);
//        gridView_home_ActionMenu_home_FoodType = view.findViewById(R.id.home_ActionMenu_home_FoodType);
//        recyclerView_home_ActionMenu_home_Discout = view.findViewById(R.id.home_ActionMenu_home_Discount);
        recyclerView_home_ActionMenu_home_Recommended = view.findViewById(R.id.home_ActionMenu_home_Recommended);
        btnandress_home = view.findViewById(R.id.btnandress_home);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AnhXa(view);

        //Special-Offers
        adapter_special_offers = new Adapter_Special_offers(getContext());
        adapter_special_offers.setData(lis_bannerSale);

        //Recommended Food
        adapter_recommended = new Adapter_Recommended(getContext());
        slideAdapter = new SlideAdapter(requireActivity());
        initData();
        VpSlide.setAdapter(slideAdapter);
        CrSlide.setViewPager(VpSlide);
        slideAdapter.registerDataSetObserver(CrSlide.getDataSetObserver());
        autoSlide();
        nextandress();
    }
    private void nextandress() {
        btnandress_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Introducts.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void initData() {
        imageFood=new ArrayList<>();
        list = new ArrayList<>();
        ApiService.API_SERVICER.getProductList().enqueue(new Callback<ListProduct>() {
            @Override
            public void onResponse(Call<ListProduct> call, Response<ListProduct> response) {
                Log.d("TAG", "onResponse: " + response.body());
                listProduct = response.body();
                for (int i = 0; i < listProduct.getProduct().size(); i++) {
                    list.add(listProduct.getProduct().get(i));
                }
                assert listProduct != null;

                for (int i = 0; i < 3; i++) {
                    Product product = list.get(i);
                    lis_food.add(new Food(product.getImage(), product.getName(), 1.8, 4.8, 1, product.getPrice(), 2.00));
                }
                adapter_recommended.setData(lis_food);

                LinearLayoutManager manager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView_home_ActionMenu_home_Recommended.setLayoutManager(manager1);
                recyclerView_home_ActionMenu_home_Recommended.setAdapter(adapter_recommended);

                for (int i=0;i<listProduct.getProduct().size();i++){
                    if (listProduct.getProduct().get(i).getDisplay()){
                        imageFood.add(listProduct.getProduct().get(i).getImage());
                    }
                }
                slideAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListProduct> call, Throwable t) {

            }
        });
        slideAdapter.setData(imageFood);

        img_home_ActionMenu_home_MyCart.setOnClickListener(v ->{
            startActivity(new Intent(getActivity(), Notification_screen.class));
        });
    }

    private void autoSlide() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (VpSlide.getCurrentItem() == imageFood.size() - 1) {
                            VpSlide.setCurrentItem(0);
                        } else {
                            VpSlide.setCurrentItem(VpSlide.getCurrentItem() + 1);
                        }
                    }
                });
            }
        }, 1000, 3000);
    }
}