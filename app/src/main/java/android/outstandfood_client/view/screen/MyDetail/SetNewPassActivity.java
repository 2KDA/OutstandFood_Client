package android.outstandfood_client.view.screen.MyDetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.outstandfood_client.OutstandActivity;
import android.outstandfood_client.R;
import android.outstandfood_client.databinding.ActivitySetNewPassBinding;
import android.outstandfood_client.databinding.FragmentProfileBinding;
import android.outstandfood_client.interfaceApi.ApiClient;
import android.outstandfood_client.interfaceApi.ApiServiceUser;
import android.outstandfood_client.models.User;
import android.outstandfood_client.object.CommonActivity;
import android.outstandfood_client.object.SharedPrefsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetNewPassActivity extends OutstandActivity {
    private ActivitySetNewPassBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetNewPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User savedUser = SharedPrefsManager.getUser(this);


        binding.imgbackpassDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnsetnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = binding.passold.getText().toString();
                String newPassword = binding.passnew.getText().toString();
                String newPassword1 = binding.passnew1.getText().toString();
                if (oldPassword.isEmpty() || newPassword.isEmpty() || newPassword1.isEmpty()) {
                    show("Outstand'Food", "Bạn cần nhập đầy đủ thông tin");
                }else if(newPassword.length() <= 6 || newPassword1.length() <= 6){
                    show("Outstand'Food", "Mật khẩu phải dài hơn 6 kí tự");
                }else if(!newPassword.equals(newPassword1)){
                    show("Outstand'Food", "Mật khẩu mới không trùng khớp");
                }else {
                    showWaitProgress(SetNewPassActivity.this);
                    changePassword(savedUser.getUsername(), oldPassword, newPassword);
                }
            }
        });
    }
    private void changePassword(String username, String oldPassword, String newPassword ) {
        ApiServiceUser apiService = ApiClient.getClient().create(ApiServiceUser.class);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w("TAG", "Failed to get FCM registration token", task.getException());
                    return;
                }
                String deviceToken = task.getResult();
                Call<User> call = apiService.changePassword(username, oldPassword, newPassword, deviceToken);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        hideWaitProgress();
                        if (response.isSuccessful()) {
                            User newUser = response.body();
                            if (newUser != null) {
                                SharedPrefsManager.clearUser(SetNewPassActivity.this);
                                SharedPrefsManager.saveUser(SetNewPassActivity.this, newUser);
                                CommonActivity.createAlertDialog(SetNewPassActivity.this, "Đổi mật khẩu thành công",
                                        "Outstand'Food", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                finish();
                                            }
                                        }).show();
                            }
                        } else {
                            show("Outstand'Food", "Đổi mật khẩu thất bại.");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        hideWaitProgress();
                        show("Outstand'Food", "Đổi mật khẩu thất bại.");
                    }
                });
            }
        });

    }
}