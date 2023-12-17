package android.outstandfood_client.view.screen.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.outstandfood_client.OutstandFragment;
import android.outstandfood_client.R;
import android.outstandfood_client.Utils;
import android.outstandfood_client.data.CartDatabase;
import android.outstandfood_client.data.CartModel;
import android.outstandfood_client.databinding.FragmentCartBinding;
import android.outstandfood_client.databinding.LayoutCheckoutBinding;
import android.outstandfood_client.interfaces.ApiService;
import android.outstandfood_client.models.AddressModel;
import android.outstandfood_client.models.AddressResponse;
import android.outstandfood_client.models.ListProduct;
import android.outstandfood_client.models.OrderModel;
import android.outstandfood_client.models.Product;
import android.outstandfood_client.models.User;
import android.outstandfood_client.object.CommonActivity;
import android.outstandfood_client.object.SharedPrefsManager;
import android.outstandfood_client.view.screen.adapter.CartAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends OutstandFragment {
    private FragmentCartBinding binding;
    private CartAdapter cartAdapter;
    private ArrayList<CartModel> list;
    Double sumPrice = 0.0;
    private double totalPriceVnd = 100000;
    private double totalPriceUsd;
    private double usdExchangeRate = 24282;
    ArrayAdapter<AddressModel> adapter;

    private User savedUser;

    List<AddressModel> addressModels;
    private ArrayList<Product> products = new ArrayList<>();

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(getLayoutInflater(), container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
        setDataSumMoney();

    }

    private void initData() {
        list = new ArrayList<>();
        list = (ArrayList<CartModel>) CartDatabase.getInstance(getActivity()).cartDao().getAllCart();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        savedUser = SharedPrefsManager.getUser(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
        binding.recyCart.setLayoutManager(manager);
        cartAdapter = new CartAdapter(requireActivity(), new CartAdapter.InterCart() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void UpdateCart(CartModel cartModel) {
                ApiService.API_SERVICER.getProductList().enqueue(new Callback<ListProduct>() {
                    @Override
                    public void onResponse(Call<ListProduct> call, Response<ListProduct> response) {
                        ListProduct listProduct = response.body();
                        products = listProduct.getProduct();
                        for (int i = 0; i < products.size(); i++) {
                            if (products.get(i).get_id().equals(cartModel.getId())) {
                                if (products.get(i).getQuantity() <= cartModel.getQuantityFood()) {
                                    Utils.showCustomToast(requireActivity(), "Hết hàng");
                                    return;
                                }
                                cartModel.setQuantityFood(cartModel.getQuantityFood() + 1);
                                CartDatabase.getInstance(getActivity()).cartDao().UpdateCart(cartModel);
                                cartAdapter.notifyDataSetChanged();
                                setDataSumMoney();
                                break;
                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<ListProduct> call, Throwable t) {
                    }
                });
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void RemoveCart(CartModel cartModel) {
                if (cartModel.getQuantityFood() > 1) {
                    Log.d("TAG12", "RemoveCart: " + cartModel.getQuantityFood());
                    cartModel.setQuantityFood(cartModel.getQuantityFood() - 1);
                    CartDatabase.getInstance(getActivity()).cartDao().UpdateCart(cartModel);
                    setDataSumMoney();
                } else {
                    CommonActivity.createDialog(getActivity(),
                            "\n Xóa sản phẩm khỏi giỏ hàng \n",
                            "Xác nhận",
                            "Đồng ý",
                            "Hủy",
                            v -> {
                                CartDatabase.getInstance(getActivity()).cartDao().DeleteCart(cartModel);
                                list = (ArrayList<CartModel>) CartDatabase.getInstance(getActivity()).cartDao().getAllCart();
                                cartAdapter.setData(list);
                                cartAdapter.notifyDataSetChanged();
                                setDataSumMoney();
                            }, null).show();
                }
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void DeleteCart(CartModel cartModel) {

                CommonActivity.createDialog(getActivity(),
                        "\n Xóa sản phẩm khỏi giỏ hàng \n",
                        "Xác nhận",
                        "Đồng ý",
                        "Hủy",
                        v -> {
                            CartDatabase.getInstance(getActivity()).cartDao().DeleteCart(cartModel);
                            list = (ArrayList<CartModel>) CartDatabase.getInstance(getActivity()).cartDao().getAllCart();
                            cartAdapter.setData(list);
                            cartAdapter.notifyDataSetChanged();
                            setDataSumMoney();
                        }, null).show();
            }
        });
        cartAdapter.setData(list);
        binding.recyCart.setAdapter(cartAdapter);
        // Init paypal
        /*LayoutCheckoutBinding layoutCheckoutBinding=LayoutCheckoutBinding.inflate(getLayoutInflater());
         */
        //
        binding.constraintAdd.setOnClickListener(view -> {
            if (list.isEmpty()) {
                Toast.makeText(requireActivity(), "không có sản phẩm", Toast.LENGTH_SHORT).show();
                return;
            }
            BottomSheetDialog sheetDialog = new BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme);
            LayoutCheckoutBinding layoutCheckoutBinding = LayoutCheckoutBinding.inflate(getLayoutInflater());

            InitPaypal(layoutCheckoutBinding);
            sheetDialog.setContentView(layoutCheckoutBinding.getRoot());
            Window window = sheetDialog.getWindow();
            assert window != null;
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            setDataSumMoney();
            layoutCheckoutBinding.imBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sheetDialog.dismiss();
                }
            });
            ApiService.API_SERVICER.getAddressList(savedUser.get_id()).enqueue(new Callback<AddressResponse>() {
                @Override
                public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                    AddressResponse addressResponse = response.body();
                    if (addressResponse == null) {
                        return;
                    }
                    addressModels = addressResponse.getAddress();
                    adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, addressModels);
                    Log.d("TAG11", "onResponse: " + addressModels.size());
                    layoutCheckoutBinding.spinnerCity.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<AddressResponse> call, Throwable t) {

                }
            });
            layoutCheckoutBinding.tvSumPrice.setText((int) Math.round(CartDatabase.getInstance(getActivity()).cartDao().sumMoney()) + " VNĐ");
            layoutCheckoutBinding.checkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layoutCheckoutBinding.loading.setVisibility(View.VISIBLE);
                    list = (ArrayList<CartModel>) CartDatabase.getInstance(getActivity()).cartDao().getAllCart();
                    ArrayList<Integer> quantity = new ArrayList<>();
                    ArrayList<String> idProduct = new ArrayList<>();
                    Log.d("TAG", "onClick: " + list.size());
                    for (int i = 0; i < list.size(); i++) {
                        quantity.add(list.get(i).getQuantityFood());
                        idProduct.add(list.get(i).getId());
                    }
                    AddressModel addressModel = (AddressModel) layoutCheckoutBinding.spinnerCity.getSelectedItem();
                    if (addressModel == null) {
                        Utils.showCustomToast(requireActivity(), "Chọn địa chỉ");
                    }
                    OrderToServer(savedUser.get_id(), false, addressModel.getId(), "Chua thanh toán", quantity, idProduct, layoutCheckoutBinding);
                }
            });
            sheetDialog.show();
        });
    }

    @SuppressLint("SetTextI18n")
    public void setDataSumMoney() {
        if (list == null) {
            binding.tvSumPrice.setText("0");
        } else {
            sumPrice = (double) Math.round(CartDatabase.getInstance(getActivity()).cartDao().sumMoney());
            binding.tvSumPrice.setText(sumPrice + " VNĐ");
        }
    }

    private void InitPaypal(LayoutCheckoutBinding layoutCheckoutBinding) {
        PaymentButtonContainer paymentButtonContainer;
        paymentButtonContainer = layoutCheckoutBinding.paymentButtonContainer;
        totalPriceUsd = sumPrice / usdExchangeRate;
        Log.d("price", totalPriceUsd + "");
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
                                                        .value(((double) Math.floor(totalPriceUsd * 100) / 100) + "")
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
                                ArrayList<Integer> quantity = new ArrayList<>();
                                ArrayList<String> idProduct = new ArrayList<>();
                                for (int i = 0; i < list.size(); i++) {
                                    quantity.add(list.get(i).getQuantityFood());
                                    idProduct.add(list.get(i).getId());
                                }
                                AddressModel addressModel = (AddressModel) layoutCheckoutBinding.spinnerCity.getSelectedItem();
                                if (addressModel == null) {
                                    Utils.showCustomToast(requireActivity(), "Hãy chọn địa chỉ");
                                }
                                OrderToServer(savedUser.get_id(), true, addressModel.getId(), "Đã thanh toán", quantity, idProduct, layoutCheckoutBinding);
                                Utils.showCustomToast(requireActivity(), "Đã thanh toán");
                            }
                        });
                    }
                }
        );
    }

    private void OrderToServer(String id_User, Boolean pay_status, String id_address, String method, ArrayList<Integer> quantity, ArrayList<String> id_product, LayoutCheckoutBinding layoutCheckoutBinding) {
        OrderModel orderModel = new OrderModel(id_User, pay_status, id_address, method, quantity, id_product);
        ApiService.API_SERVICER.addOrder(orderModel).enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(@NonNull Call<OrderModel> call, @NonNull Response<OrderModel> response) {
                Toast.makeText(requireActivity(), "Thanh cong", Toast.LENGTH_SHORT).show();
                layoutCheckoutBinding.loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<OrderModel> call, @NonNull Throwable t) {
                Toast.makeText(requireActivity(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}