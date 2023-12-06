package android.outstandfood_client.view.screen.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.outstandfood_client.databinding.FragmentMenuBinding;
import android.outstandfood_client.interfaces.ApiService;
import android.outstandfood_client.interfaces.FoodInterface;
import android.outstandfood_client.models.Category;
import android.outstandfood_client.models.ListCategory;
import android.outstandfood_client.view.screen.FoodActivity;
import android.outstandfood_client.view.screen.adapter.ListCateAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.outstandfood_client.R;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFragment extends Fragment implements FoodInterface {
    private ListCateAdapter listCateAdapter;
    private FragmentMenuBinding binding;
    private ListCategory categoryArrayList;

    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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
        binding=FragmentMenuBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        binding.progressCircular.setVisibility(View.VISIBLE);
        LinearLayoutManager manager=new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL,false);
        binding.recyCate.setLayoutManager(manager);
        listCateAdapter=new ListCateAdapter(this);
        ApiService.API_SERVICER.getListCate().enqueue(new Callback<ListCategory>() {
            @Override
            public void onResponse(Call<ListCategory> call, Response<ListCategory> response) {
                categoryArrayList=response.body();
                Log.d("TAG", "onResponse: "+categoryArrayList.getCategory().size());
                listCateAdapter.setData(categoryArrayList.getCategory());
                binding.recyCate.setAdapter(listCateAdapter);
                binding.progressCircular.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ListCategory> call, Throwable t) {

            }
        });

    }

    @Override
    public void showFood(String id,String name) {
        Intent intent=new Intent(requireActivity(), FoodActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("idCate",id);
        bundle.putString("name",name);
        intent.putExtra("intent",bundle);
        startActivity(intent);
    }


}