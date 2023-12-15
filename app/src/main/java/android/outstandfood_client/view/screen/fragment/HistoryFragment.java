package android.outstandfood_client.view.screen.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.outstandfood_client.databinding.FragmentHistoryBinding;
import android.outstandfood_client.interfaces.ApiService;
import android.outstandfood_client.models.HistoryModel;
import android.outstandfood_client.view.screen.adapter.HistoryAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.outstandfood_client.R;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentHistoryBinding.inflate(getLayoutInflater());
        return (binding.getRoot());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        binding.loading.setVisibility(View.VISIBLE);
        historyAdapter=new HistoryAdapter();
        LinearLayoutManager manager=new LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false);
        binding.recyHistory.setLayoutManager(manager);
        ApiService.API_SERVICER.getOrderById("KH005").enqueue(new Callback<List<HistoryModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<HistoryModel>> call, Response<List<HistoryModel>> response) {
                list= (ArrayList<HistoryModel>) response.body();
                Log.d("TAG", "onResponse: "+list.size());
                historyAdapter.setData(list);
                historyAdapter.notifyDataSetChanged();
                binding.loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<HistoryModel>> call, Throwable t) {
                Toast.makeText(requireActivity(), "fail", Toast.LENGTH_SHORT).show();
            }
        });
        binding.recyHistory.setAdapter(historyAdapter);
    }
}