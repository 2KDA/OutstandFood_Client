package android.outstandfood_client.view.screen.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.outstandfood_client.databinding.FragmentCartBinding;
import android.outstandfood_client.databinding.LayoutCheckoutBinding;
import android.outstandfood_client.view.screen.adapter.CartAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.outstandfood_client.R;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;
import com.paypal.checkout.order.OrderRequest;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButtonContainer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private FragmentCartBinding binding;
    private CartAdapter cartAdapter;
    private ArrayList<String> listFood;
    private double totalPriceVnd=100000;
    private double totalPriceUsd;
    private double usdExchangeRate=24282;
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
        // Init paypal
        /*LayoutCheckoutBinding layoutCheckoutBinding=LayoutCheckoutBinding.inflate(getLayoutInflater());
        */
        //
        binding.constraintAdd.setOnClickListener(view -> {
            BottomSheetDialog sheetDialog=new BottomSheetDialog(requireActivity());
            LayoutCheckoutBinding layoutCheckoutBinding=LayoutCheckoutBinding.inflate(getLayoutInflater());
            InitPaypal(layoutCheckoutBinding);
            sheetDialog.setContentView(layoutCheckoutBinding.getRoot());
            Window window=sheetDialog.getWindow();
            assert window != null;
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            layoutCheckoutBinding.checkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TAG", "onClick: ssss");
                }
            });
            sheetDialog.show();
        });
    }

    private void InitPaypal(LayoutCheckoutBinding layoutCheckoutBinding){
        PaymentButtonContainer paymentButtonContainer;
        paymentButtonContainer=layoutCheckoutBinding.paymentButtonContainer;
        totalPriceUsd=totalPriceVnd/usdExchangeRate;
        Log.d("price",totalPriceUsd+"");
        // ...
        paymentButtonContainer.setup(
                new CreateOrder() {
                    @Override
                    public void create(@NotNull CreateOrderActions createOrderActions) {
                        ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
                        purchaseUnits.add(
                                new PurchaseUnit.Builder()
                                        .amount(
                                                new Amount.Builder()
                                                        .currencyCode(CurrencyCode.USD)
                                                        .value(((double) Math.floor(totalPriceUsd * 100) / 100)+"")
                                                        .build()
                                        )
                                        .build()
                        );
                        OrderRequest order = new OrderRequest(
                                OrderIntent.CAPTURE,
                                new AppContext.Builder()
                                        .userAction(UserAction.PAY_NOW)
                                        .build(),
                                purchaseUnits
                        );
                        createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                    }
                },
                new OnApprove() {
                    @Override
                    public void onApprove(@NotNull Approval approval) {
                        approval.getOrderActions().capture(new OnCaptureComplete() {
                            @Override
                            public void onCaptureComplete(@NotNull CaptureOrderResult result) {
                                Log.i("CaptureOrder", String.format("CaptureOrderResult: %s", result));
                                Toast.makeText(getContext(), "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
        );
    }

}