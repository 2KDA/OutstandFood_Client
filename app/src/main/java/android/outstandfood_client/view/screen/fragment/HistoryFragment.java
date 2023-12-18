package android.outstandfood_client.view.screen.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.outstandfood_client.OutstandFragment;
import android.outstandfood_client.databinding.FragmentHistoryBinding;
import android.outstandfood_client.interfaces.ApiService;
import android.outstandfood_client.interfaces.FoodInterface;
import android.outstandfood_client.interfaces.HisAdapter;
import android.outstandfood_client.models.HistoryModel;
import android.outstandfood_client.models.ListDetail;
import android.outstandfood_client.models.Product;
import android.outstandfood_client.models.User;
import android.outstandfood_client.object.SharedPrefsManager;
import android.outstandfood_client.view.screen.FoodHistoryActivity;
import android.outstandfood_client.view.screen.AddRatingActivity;
import android.outstandfood_client.view.screen.Login_screen;
import android.outstandfood_client.view.screen.adapter.HistoryAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.orhanobut.hawk.Hawk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends OutstandFragment implements HisAdapter, FoodInterface {
    private FragmentHistoryBinding binding;
    private ArrayList<HistoryModel> list;
    private HistoryAdapter historyAdapter;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(SharedPrefsManager.getUser(getContext()) != null){
            binding.layoutRcv.setVisibility(View.VISIBLE);
            binding.layoutBtnLogin.setVisibility(View.GONE);
        }else{
            binding.layoutRcv.setVisibility(View.GONE);
            binding.layoutBtnLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void addRating(User user) {
        Intent i = new Intent(requireActivity(), AddRatingActivity.class);
        i.putExtra("User", user);
        startActivity(i);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentHistoryBinding.inflate(getLayoutInflater());
        return (binding.getRoot());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Hawk.init(requireActivity()).build();
        initView();
    }

    private void initView() {
        binding.loading.setVisibility(View.VISIBLE);
        historyAdapter = new HistoryAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recyHistory.setLayoutManager(manager);
        User user = SharedPrefsManager.getUser(getContext());
        if (user != null) {
            ApiService.API_SERVICER.getOrderById(user.get_id()).enqueue(new Callback<List<HistoryModel>>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(Call<List<HistoryModel>> call, Response<List<HistoryModel>> response) {
                    list = (ArrayList<HistoryModel>) response.body();
                    Log.d("TAG15", "onResponse: " + list.size());
                    historyAdapter.setData(list);
                    historyAdapter.notifyDataSetChanged();
                    binding.loading.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<List<HistoryModel>> call, Throwable t) {
                    Log.d("TAG15", "onFailure: ");
                    binding.loading.setVisibility(View.GONE);
                }
            });
        }
        binding.recyHistory.setAdapter(historyAdapter);
        binding.btnLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Login_screen.class));
                getActivity().finish();
            }
        });
    }

    @Override
    public void showFood(String id, String name) {

    }

    @Override
    public void addFood(Product product) {

    }

    @Override
    public void DetailFood(Product product) {

    }


    @Override
    public void hisAdapter(ArrayList<ListDetail> listDetail) {
        Hawk.put("list", listDetail);
        Intent intent=new Intent(requireActivity(), FoodHistoryActivity.class);
        startActivity(intent);
    }
}