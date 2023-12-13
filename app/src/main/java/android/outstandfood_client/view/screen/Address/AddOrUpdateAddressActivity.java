package android.outstandfood_client.view.screen.Address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.outstandfood_client.OutstandActivity;
import android.outstandfood_client.R;
import android.outstandfood_client.databinding.ActivityAddOrUpdateAddressBinding;
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
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddOrUpdateAddressActivity extends OutstandActivity {
    private ActivityAddOrUpdateAddressBinding binding;
    static final String BASE_URL = "https://outstanfood-com.onrender.com/api/";
    private AddressAdapter addressAdapter;
    private List<AddressModel> addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddOrUpdateAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AddressModel addressModel = (AddressModel) getIntent().getSerializableExtra("address_model");
        User savedUser = SharedPrefsManager.getUser(this);
        String addressId = getIntent().getStringExtra("_idAddress");

        if (addressId == null || addressId.isEmpty()) {
          binding.txtman.setText("Thêm địa chỉ");
          binding.btnaddress.setText("Tạo mới");
            binding.btnaddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String address = binding.edtaddress.getText().toString().trim();
                    String phone = binding.edtphone.getText().toString().trim();
                    if (address.isEmpty() || phone.isEmpty()) {
                        show("Outstand'Food", "Vui lòng nhập đầy đủ thông tin.");
                    }
                    else if (!isValidPhoneNumber(phone)) {
                        show("Outstand'Food", "Số điện thoại không hợp lệ");
                    }
                    else {
                        showWaitProgress(AddOrUpdateAddressActivity.this);
                        addAddress(savedUser.get_id(), address, phone);
                    }
                }
            });

        } else if (addressModel.getId() != " " || addressModel.getAddress() != null) {
            binding.txtman.setText("Sửa địa chỉ");
            binding.btnaddress.setText("Sửa địa chỉ");

            binding.edtphone.setText(addressModel.getPhone());
            binding.edtaddress.setText(addressModel.getAddress());
            binding.btnaddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String address = binding.edtaddress.getText().toString().trim();
                    String phone = binding.edtphone.getText().toString().trim();
                    if (address.isEmpty() || phone.isEmpty()) {
                        show("Outstand'Food", "Vui lòng nhập đầy đủ thông tin.");
                    }else if (!isValidPhoneNumber(phone)) {
                        show("Outstand'Food", "Số điện thoại không hợp lệ.");
                    }
                    else {
                        showWaitProgress(AddOrUpdateAddressActivity.this);
                        updateAddress(addressModel.getId(), address, phone);
                    }
                }
            });
        }

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void addAddress(String id_user, String address, String phone) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServiceAddress addresInterface = retrofit.create(ApiServiceAddress.class);

        Call<AddressModel> call = addresInterface.addAddress(id_user, address, phone);
        call.enqueue(new Callback<AddressModel>() {
            @Override
            public void onResponse(Call<AddressModel> call, Response<AddressModel> response) {
                hideWaitProgress();
                if (response.isSuccessful() && response.body() != null) {
                    CommonActivity.createAlertDialog(AddOrUpdateAddressActivity.this,"Thêm địa chỉ thành công",
                            "Outstand'Food",new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent returnIntent = new Intent();
                                    returnIntent.putExtra("result", RESULT_OK);
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    finish();
                                }
                            }
                    ).show();
                } else {
                    show("Outsand'Food", "Lỗi thêm địa chỉ.");
                }
            }
            @Override
            public void onFailure(Call<AddressModel> call, Throwable t) {
                hideWaitProgress();
                show("Outstand'Food", "Lỗi : " + t.getMessage());
            }
        });
    }
    private void updateAddress(String id, String address, String phone) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServiceAddress addressInterface = retrofit.create(ApiServiceAddress.class);

        Call<AddressModel> call = addressInterface.updateAddress(id, address, phone);
        call.enqueue(new Callback<AddressModel>() {
            @Override
            public void onResponse(Call<AddressModel> call, Response<AddressModel> response) {
                hideWaitProgress();
                if (response.isSuccessful() && response.body() != null) {
                    CommonActivity.createAlertDialog(AddOrUpdateAddressActivity.this,"Sửa địa chỉ thành công.",
                            "Outstand'Food",new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent returnIntent = new Intent();
                                    returnIntent.putExtra("result", RESULT_OK);
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    finish();
                                }
                            }
                    ).show();
                } else {
                    show("Outsand'Food", "Lỗi sửa địa chỉ.");
                }
            }

            @Override
            public void onFailure(Call<AddressModel> call, Throwable t) {
                hideWaitProgress();
                show("Outstand'Food", "Lỗi : " + t.getMessage());
             }
        });
    }
    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "0[1-9][0-9]{8}";
        return phoneNumber.matches(regex);
    }
}