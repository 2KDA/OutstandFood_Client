package android.outstandfood_client.view.screen.MyDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.outstandfood_client.R;
import android.outstandfood_client.databinding.ActivitySetNewPassBinding;
import android.outstandfood_client.databinding.FragmentProfileBinding;
import android.outstandfood_client.interfaceApi.ApiClient;
import android.outstandfood_client.interfaceApi.ApiServiceUser;
import android.outstandfood_client.models.User;
import android.outstandfood_client.object.SharedPrefsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetNewPassActivity extends AppCompatActivity {
    private ActivitySetNewPassBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetNewPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User savedUser = SharedPrefsManager.getUser(this);

        binding.show.setVisibility(View.GONE);

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
                    Toast.makeText(SetNewPassActivity.this, "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else if(newPassword.length() <= 6 || newPassword1.length() <= 6){
                    Toast.makeText(SetNewPassActivity.this, "Độ dài mật khẩu cần trên 6 kí tự", Toast.LENGTH_SHORT).show();
                }
                else if(!newPassword.equals(newPassword1)){
                    Toast.makeText(SetNewPassActivity.this, "mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                }
                else {
                    changePassword(savedUser.getUsername(), oldPassword, newPassword);
                }
            }
        });
    }
    private void changePassword(String username, String oldPassword, String newPassword ) {
        ApiServiceUser apiService = ApiClient.getClient().create(ApiServiceUser.class);

        Call<Void> call = apiService.changePassword(username, oldPassword, newPassword );
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    User updatedUser = new User();
                    updatedUser.setPassword(newPassword);
                    SharedPrefsManager.saveUser(SetNewPassActivity.this, updatedUser);
                    binding.show.setVisibility(View.VISIBLE);
                    finish();
                    Toast.makeText(SetNewPassActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Lõi" ,"Lỗi " + response);
                    Toast.makeText(SetNewPassActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Lõi" ,"Lỗi " + t.getMessage());
                Toast.makeText(SetNewPassActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
    }
}