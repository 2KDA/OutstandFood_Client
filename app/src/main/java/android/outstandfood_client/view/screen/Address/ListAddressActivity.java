package android.outstandfood_client.view.screen.Address;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.outstandfood_client.R;
import android.outstandfood_client.databinding.ActivityListAddressBinding;
import android.outstandfood_client.interfaceApi.ApiServiceAddress;
import android.outstandfood_client.models.AddressModel;
import android.outstandfood_client.models.AddressResponse;
import android.outstandfood_client.models.User;
import android.outstandfood_client.object.SharedPrefsManager;
import android.outstandfood_client.view.screen.adapter.AddressAdapter;
import android.util.Log;
import android.view.View;
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

public class ListAddressActivity extends AppCompatActivity {
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


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_ADDRESS) {
            if (resultCode == Activity.RESULT_OK) {
                // Thực hiện tải lại danh sách địa chỉ
                User savedUser = SharedPrefsManager.getUser(this);
                if (savedUser != null) {
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
                if (response.isSuccessful()) {
                    AddressResponse addressResponse = response.body();
                    if (addressResponse != null && addressResponse.getAddress() != null) {
                        addressList.clear();
                        addressList.addAll(addressResponse.getAddress());
                        addressAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(ListAddressActivity.this, "Không có địa chỉ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                   // Toast.makeText(ListAddressActivity.this, "Lỗi: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                Log.e("ListAddressActivity", "Lỗi: ", t);
              //  Toast.makeText(ListAddressActivity.this, "Lỗi dài: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void showActionDialog(AddressModel address) {
        CharSequence[] items = {"Update", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ListAddressActivity.this);
        builder.setTitle("Chọn hành động");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent = new Intent(ListAddressActivity.this, AddOrUpdateAddressActivity.class);
                    intent.putExtra("address_model", address);
                    intent.putExtra("_idAddress", address.getId());
                    startActivityForResult(intent, REQUEST_CODE_ADD_ADDRESS);
                } else if (which == 1) {
                    // Xử lý Delete - Hiển thị dialog xác nhận xóa
                    showConfirmDeleteDialog(address);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showConfirmDeleteDialog(AddressModel address) {
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(ListAddressActivity.this);
        confirmBuilder.setTitle("Xác nhận xóa");
        confirmBuilder.setMessage("Bạn có chắc chắn muốn xóa địa chỉ này không?");
        confirmBuilder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteAddress(address.getId());
            }
        });
        confirmBuilder.setNegativeButton("Hủy", null);
        AlertDialog confirmDialog = confirmBuilder.create();
        confirmDialog.show();
    }
    private void reloadAddressData() {
        User savedUser = SharedPrefsManager.getUser(this);
        if (savedUser != null) {
            getAddressByUserId(savedUser.get_id());
        }
    }
    private void deleteAddress(String id) {
        Log.d("ListAddressActivity", "Deleting address with ID: " + id);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServiceAddress addressInterface = retrofit.create(ApiServiceAddress.class);

        Call<Void> call = addressInterface.deleteAddress(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("ListAddressActivity", "Delete successful");
                    Toast.makeText(ListAddressActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    reloadAddressData();
                } else {
                    Log.e("ListAddressActivity", "Delete failed: " + response.message());
                    Toast.makeText(ListAddressActivity.this, "Lỗi khi xóa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("ListAddressActivity", "Error calling API", t);
                Toast.makeText(ListAddressActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }

}