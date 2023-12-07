package android.outstandfood_client.view.screen.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.outstandfood_client.databinding.FragmentCartBinding;
import android.outstandfood_client.view.screen.adapter.CartAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.outstandfood_client.R;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private FragmentCartBinding binding;
    private CartAdapter cartAdapter;
    private ArrayList<String> listFood;
    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentCartBinding.inflate(getLayoutInflater(),container,false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        listFood=new ArrayList<>();
        listFood.add("aaaa");
        listFood.add("aaaa");
        listFood.add("aaaa");
        listFood.add("aaaa");
        listFood.add("aaaa");
        listFood.add("aaaa");listFood.add("aaaa");listFood.add("aaaa");listFood.add("aaaa");listFood.add("aaaa");


    }

    private void initView() {
        LinearLayoutManager manager=new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL,false);
        binding.recyCart.setLayoutManager(manager);
        cartAdapter=new CartAdapter();
        cartAdapter.setData(listFood);
        binding.recyCart.setAdapter(cartAdapter);
        binding.constraintAdd.setOnClickListener(view -> {
            BottomSheetDialog sheetDialog=new BottomSheetDialog(requireActivity());
            sheetDialog.setContentView(R.layout.layout_checkout);
            Window window=sheetDialog.getWindow();
            assert window != null;
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            sheetDialog.show();
        });
    }


}