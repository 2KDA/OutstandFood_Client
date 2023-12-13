package android.outstandfood_client.view.screen.Address;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.outstandfood_client.OutstandActivity;
import android.outstandfood_client.R;
import android.outstandfood_client.databinding.ActivityListAddressBinding;
import android.outstandfood_client.interfaceApi.ApiServiceAddress;
import android.outstandfood_client.models.AddressModel;
import android.outstandfood_client.models.AddressResponse;
import android.outstandfood_client.models.User;
import android.outstandfood_client.object.CommonActivity;
import android.outstandfood_client.object.SharedPrefsManager;
import android.outstandfood_client.view.screen.MyDetail.MydetailActivity;
import android.outstandfood_client.view.screen.adapter.AddressAdapter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListAddressActivity extends OutstandActivity {
    private static final int REQUEST_CODE_ADD_ADDRESS = 1;
    private ActivityListAddressBinding binding;
    private RecyclerView recyclerView;
    private AddressAdapter addressAdapter;
    private List<AddressModel> addressList;

    static final String BASE_URL = "https://outstanfood-com.onrender.com/api/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User savedUser = SharedPrefsManager.getUser(this);
        recyclerView = findViewById(R.id.recyclerviewaddress);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addressList = new ArrayList<>();
        addressAdapter = new AddressAdapter(this, addressList, new AddressAdapter.OnAddressClickListener() {
            @Override
            public void onAddressClick(AddressModel address) {
                showActionDialog(address);
            }
        });
        recyclerView.setAdapter(addressAdapter);
        if (savedUser != null) {
            showWaitProgress(ListAddressActivity.this);
            getAddressByUserId(savedUser.get_id());
        }
        binding.btnaddaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListAddressActivity.this, AddOrUpdateAddressActivity.class);
                intent.putExtra("_idAddress", "");
                startActivityForResult(intent, REQUEST_CODE_ADD_ADDRESS);
            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_ADDRESS) {
            if (resultCode == Activity.RESULT_OK) {
                // Thực hiện tải lại danh sách địa chỉ
                User savedUser = SharedPrefsManager.getUser(this);
                if (savedUser != null) {
                    showWaitProgress(ListAddressActivity.this);
                    getAddressByUserId(savedUser.get_id());
                }
            }
        }
    }

    private void getAddressByUserId(String userId) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServiceAddress apiServiceAddress = retrofit.create(ApiServiceAddress.class);

        Call<AddressResponse> call = apiServiceAddress.getAddressList(userId);
        call.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                hideWaitProgress();
                if (response.isSuccessful()) {
                    AddressResponse addressResponse = response.body();
                    if (addressResponse != null && addressResponse.getAddress() != null) {
                        addressList.clear();
                        addressList.addAll(addressResponse.getAddress());
                        addressAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                hideWaitProgress();
                show("Outstand'Food", "Lỗi : " + t.getMessage());
            }

        });
    }

    private void showActionDialog(AddressModel address) {

        try {
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_common);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            TextView tvTitle = (TextView) dialog.findViewById(R.id.tvDialogTitle);
            tvTitle.setText("Outstand'Food");
            TextView tvMessage = (TextView) dialog.findViewById(R.id.tvDialogContent);
            tvMessage.setText("Chọn Hành động");

            Button btnLeft = (Button) dialog.findViewById(R.id.btnLeft);
            Button btnRight = (Button) dialog.findViewById(R.id.btnRight);
            btnLeft.setText("Sửa");
            btnRight.setText("Xóa");
            btnRight.setBackgroundColor(getColor(R.color.colorRed));
            dialog.show();
            View.OnClickListener leftClickListener = new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(ListAddressActivity.this, AddOrUpdateAddressActivity.class);
                    intent.putExtra("address_model", address);
                    intent.putExtra("_idAddress", address.getId());
                    startActivityForResult(intent, REQUEST_CODE_ADD_ADDRESS);
                    dialog.dismiss();
                }
            };
            View.OnClickListener rightClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    showConfirmDeleteDialog(address);
                    dialog.dismiss();
                }
            };
            btnLeft.setOnClickListener(leftClickListener);
            btnRight.setOnClickListener(rightClickListener);

        } catch (Exception e) {
            show("Lỗi", e.getMessage());
        }
    }

    private void showConfirmDeleteDialog(AddressModel address) {
        CommonActivity.createDialog(ListAddressActivity.this,
                "Bạn có chắc chắn muốn xóa địa chỉ này không?",
                "Xác nhận xóa","Đồng ý", "Hủy",
                v -> {
                    showWaitProgress(ListAddressActivity.this);
                    deleteAddress(address.getId());
                },null).show();
    }
    private void reloadAddressData() {
        User savedUser = SharedPrefsManager.getUser(this);
        if (savedUser != null) {
            showWaitProgress(ListAddressActivity.this);
            getAddressByUserId(savedUser.get_id());
        }
    }
    private void deleteAddress(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServiceAddress addressInterface = retrofit.create(ApiServiceAddress.class);

        Call<Void> call = addressInterface.deleteAddress(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                hideWaitProgress();
                if (response.isSuccessful()) {
                    CommonActivity.createAlertDialog(ListAddressActivity.this,"Xóa thành công",
                            "Outstand'Food",new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    reloadAddressData();
                                }
                            }
                    ).show();
                } else {
                    show("Outstand'Food", "Lỗi khi xóa địa chỉ");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                hideWaitProgress();
                show("Outstand'Food", "Lỗi : " + t.getMessage());
            }
        });
    }

}