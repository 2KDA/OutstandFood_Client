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
import android.outstandfood_client.interfaces.FoodInterface;
import android.outstandfood_client.view.screen.FoodActivity;
import android.outstandfood_client.view.screen.adapter.ListCateAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.outstandfood_client.R;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MenuFragment extends Fragment implements FoodInterface {
    private ListCateAdapter listCateAdapter;
    private FragmentMenuBinding binding;

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
        listCateAdapter=new ListCateAdapter(this);
        ArrayList<String> list=new ArrayList<>();
        list.add("aaa");
        list.add("swdw");
        list.add("addfes");
        LinearLayoutManager manager=new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL,false);
        binding.recyCate.setLayoutManager(manager);
        listCateAdapter.setData(list);
        binding.recyCate.setAdapter(listCateAdapter);
    }

    @Override
    public void showFood() {
        startActivity(new Intent(requireActivity(), FoodActivity.class));
    }
}