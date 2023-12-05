package android.outstandfood_client.view.screen.MyDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.outstandfood_client.R;
import android.outstandfood_client.databinding.ActivityMydetailBinding;
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

public class MydetailActivity extends AppCompatActivity {
private ActivityMydetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMydetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User savedUser = SharedPrefsManager.getUser(this);

        binding.edtphoneprofileDetail.setText(savedUser.getPhone());
        binding.edtusernameprofileDetail.setText(savedUser.getUsername());
        binding.edtnameprofileDetail.setText(savedUser.getName());
        binding.edtemailprofileDetail.setText(savedUser.getUserEmail());

        binding.imgbackprofileDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.btnupdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.edtemailprofileDetail.getText().toString();
                String name = binding.edtnameprofileDetail.getText().toString();
                String phone = binding.edtphoneprofileDetail.getText().toString();
                if (email.isEmpty() || name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(MydetailActivity.this, "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    updateUsser(savedUser.get_id(), name, phone,email);
                }
            }
        });

    }
    private void updateUsser(String id ,String name, String phone, String userEmail ) {
        ApiServiceUser apiService = ApiClient.getClient().create(ApiServiceUser.class);

        Call<User> call = apiService.updateUser(id,name, phone, userEmail );
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MydetailActivity.this, "Đổi thông tin thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Lõi" ,"Lỗi " + response);
                    Toast.makeText(MydetailActivity.this, "thông tin cũ không đúng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Lõi" ,"Lỗi " + t.getMessage());
                Toast.makeText(MydetailActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
    }
}