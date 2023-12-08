package android.outstandfood_client.view.screen.MyDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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

import com.bumptech.glide.Glide;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MydetailActivity extends AppCompatActivity {
    private ActivityMydetailBinding binding;
    private User savedUser; // Declare the savedUser variable here
    private Uri imageUri;
    private static final int pickImage = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMydetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User savedUser = SharedPrefsManager.getUser(this);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }

        // Initialize the savedUser variable with the user data from SharedPrefsManager
        savedUser = SharedPrefsManager.getUser(this);

        RequestOptions requestOptions = new RequestOptions().transform(new CircleCrop());

        String baseUrl = "https://outstanfood-com.onrender.com/";

        Glide.with(binding.getRoot().getContext())
                .load( baseUrl+ savedUser.getImage())
                .apply(requestOptions)
                .placeholder(R.drawable.ic_person_outline_24)
                .into(binding.imgprofileDetail);

        binding.edtphoneprofileDetail.setText(savedUser.getPhone());
        binding.edtusernameprofileDetail.setText(savedUser.getUsername());
        binding.edtnameprofileDetail.setText(savedUser.getName());
        binding.edtemailprofileDetail.setText(savedUser.getUserEmail());


        Glide.with(this)
                .load("https://outstanfood-com.onrender.com/"+savedUser.getImage())
                .error(R.drawable.avartar)
                .into(binding.imgprofiledetail);
        binding.imgbackprofileDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.imgprofileDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Chọn hình ảnh"), pickImage);
            }
        });
        binding.btnupdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sau khi chọn ảnh, cập nhật thông tin người dùng với ảnh mới
                String email = binding.edtemailprofileDetail.getText().toString();
                String name = binding.edtnameprofileDetail.getText().toString();
                String phone = binding.edtphoneprofileDetail.getText().toString();
                String imageUrl = imageUri.toString(); // Chuyển Uri thành URL


                if (email.isEmpty() || name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(MydetailActivity.this, "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else if(name.length() <= 10){
                    Toast.makeText(MydetailActivity.this, "Độ dài của họ và tên cần trên 10 kí tự", Toast.LENGTH_SHORT).show();
                }
                else {
                    updateUsser(savedUser.get_id(), name, phone, email,imageUrl);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pickImage && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            if (imageUri != null) {
                Glide.with(this)
                        .load(imageUri)
                        .apply(RequestOptions.circleCropTransform())
                        .placeholder(R.drawable.ic_person_outline_24)
                        .into(binding.imgprofileDetail);

                Log.d("image" , "image " +imageUri.toString());

            }
        }
    }

    private void updateUsser(String id, String name, String phone, String userEmail, String image) {
        ApiServiceUser apiService = ApiClient.getClient().create(ApiServiceUser.class);

        Call<User> call = apiService.updateUser(id, name, phone, userEmail, image);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User newUser = response.body();
                    SharedPrefsManager.clearUser(MydetailActivity.this);
                    SharedPrefsManager.saveUser(MydetailActivity.this, newUser);
                    Toast.makeText(MydetailActivity.this, "Đổi thông tin thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Lõi", "Lỗi " + response);
                    Toast.makeText(MydetailActivity.this, "thông tin cũ không đúng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Lõi", "Lỗi " + t.getMessage());
                Toast.makeText(MydetailActivity.this, "Không có sự thay đổi nào", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
