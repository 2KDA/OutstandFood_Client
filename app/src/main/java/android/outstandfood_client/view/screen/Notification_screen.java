package android.outstandfood_client.view.screen;

import android.os.Bundle;
import android.outstandfood_client.OutstandActivity;
import android.outstandfood_client.databinding.ActivityNotificationBinding;
import android.outstandfood_client.interfaceApi.ApiServiceAddress;
import android.outstandfood_client.models.AddressModel;
import android.outstandfood_client.models.Notification;
import android.outstandfood_client.models.User;
import android.outstandfood_client.object.CommonActivity;
import android.outstandfood_client.object.SharedPrefsManager;
import android.outstandfood_client.view.screen.Address.ListAddressActivity;
import android.outstandfood_client.view.screen.adapter.NotificationAdapter;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Notification_screen extends OutstandActivity implements NotificationAdapter.OnNotificationClickListener{
    private static final int REQUEST_CODE_ADD_ADDRESS = 1;
    private ActivityNotificationBinding binding;
    private NotificationAdapter notificationAdapter;
    private ArrayList<Notification> notificationList = new ArrayList<>();;

    static final String BASE_URL = "https://outstanfood-com.onrender.com/api/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User savedUser = SharedPrefsManager.getUser(this);

        binding.rcvNotification.setLayoutManager(new LinearLayoutManager(this));

        notificationAdapter = new NotificationAdapter(notificationList,this);
        notificationAdapter.setOnNotificationClickListener(this);
        binding.rcvNotification.setAdapter(notificationAdapter);
        if (savedUser != null) {
            binding.progressCircular.setVisibility(View.VISIBLE);
            getNotificationByUserId(savedUser.get_id());
        }

        binding.imgback.setOnClickListener(view -> {
            onBackPressed();
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void getNotificationByUserId(String userId) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServiceAddress apiServiceAddress = retrofit.create(ApiServiceAddress.class);

        Call<List<Notification>> call = apiServiceAddress.getNotification(userId);
        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                binding.progressCircular.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                        notificationList.clear();
                        notificationList = (ArrayList<Notification>) response.body();
                        notificationAdapter.setData(notificationList);
                }
            }
            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                binding.progressCircular.setVisibility(View.GONE);
                show("Outstand'Food", "Lỗi : " + t.getMessage());
            }

        });
    }

    private void deleteNotification(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServiceAddress addressInterface = retrofit.create(ApiServiceAddress.class);

        Call<Void> call = addressInterface.deleteNotification(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                hideWaitProgress();
                if (response.isSuccessful()) {
                    CommonActivity.createAlertDialog(Notification_screen.this,"Xóa thành công",
                            "Outstand'Food",new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    reloadNotificationData();
                                }
                            }
                    ).show();
                } else {
                    show("Outstand'Food", "Lỗi khi xóa thông báo");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                hideWaitProgress();
                show("Outstand'Food", "Lỗi : " + t.getMessage());
            }
        });
    }
    private void showConfirmDeleteDialog(String notificationId) {
        CommonActivity.createDialog(Notification_screen.this,
                "Bạn có chắc chắn muốn xóa thông báo này không?",
                "Xác nhận xóa","Đồng ý", "Hủy",
                v -> {
                    showWaitProgress(Notification_screen.this);
                    deleteNotification(notificationId);
                },null).show();
    }
    private void reloadNotificationData() {
        User savedUser = SharedPrefsManager.getUser(this);
        if (savedUser != null) {
            showWaitProgress(Notification_screen.this);
            getNotificationByUserId(savedUser.get_id());
        }
    }
    @Override
    public void onNotificationLongClick(String notificationId) {
        showConfirmDeleteDialog(notificationId);
    }
}