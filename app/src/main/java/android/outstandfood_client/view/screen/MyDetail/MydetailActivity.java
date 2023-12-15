package android.outstandfood_client.view.screen.MyDetail;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.outstandfood_client.MainActivity;
import android.outstandfood_client.OutstandActivity;
import android.outstandfood_client.R;
import android.outstandfood_client.databinding.ActivityMydetailBinding;
import android.outstandfood_client.interfaceApi.ApiClient;
import android.outstandfood_client.interfaceApi.ApiServiceUser;
import android.outstandfood_client.models.User;
import android.outstandfood_client.object.CommonActivity;
import android.outstandfood_client.object.SharedPrefsManager;
import android.outstandfood_client.view.screen.Login_screen;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MydetailActivity extends OutstandActivity {
    private ActivityMydetailBinding binding;
    private User savedUser; // Declare the savedUser variable here
    private Uri imageUri;
    private File imageFile;
    private User userInfo = new User() ;

    private static final int pickImage = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMydetailBinding.inflate(getLayoutInflater());
        userInfo = SharedPrefsManager.getUser(this);
        setContentView(binding.getRoot());

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }

        // Initialize the savedUser variable with the user data from SharedPrefsManager
        savedUser = SharedPrefsManager.getUser(this);

        RequestOptions requestOptions = new RequestOptions().transform(new CircleCrop());

        binding.edtphoneprofileDetail.setText(savedUser.getPhone());
        binding.edtusernameprofileDetail.setText(savedUser.getUsername());
        binding.edtnameprofileDetail.setText(savedUser.getName());
        binding.edtemailprofileDetail.setText(savedUser.getUserEmail());


        Glide.with(this)
                .load(savedUser.getImage())
                .error(R.drawable.avartar)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.imgprofiledetail);
        binding.imgbackprofileDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.imgprofiledetail.setOnClickListener(new View.OnClickListener() {
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
                if (email.isEmpty() || name.isEmpty() || phone.isEmpty()) {
                    show("Outsand'Food" , "Vui lòng nhập đầy đủ thông tin.");
                } else if (name.length() <= 7) {
                    show("Outsand'Food" , "Họ tên quá ngắn. Không hợp lệ.");
                } else {
                    showWaitProgress(MydetailActivity.this);
                    if (imageFile != null) {
                        updateUser(savedUser.get_id(), name, phone, email, imageFile);
                    } else {
                        updateUser(savedUser.get_id(), name, phone, email, null);
                    }
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
                String imagePath = getCroppedImagePath(imageUri);
                if (imagePath != null) {
                    imageFile = new File(imagePath);
                    Glide.with(this)
                            .load(imageFile)
                            .apply(RequestOptions.circleCropTransform())
                            .placeholder(R.drawable.ic_person_outline_24)
                            .into(binding.imgprofiledetail);
                }
            }
        }
    }

    private String getCroppedImagePath(Uri uri) {
        String imagePath = null;
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                Bitmap originalBitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                String imageFileName = "cropped_image_" + timeStamp + ".png";
                File file = new File(getCacheDir(), imageFileName);
                FileOutputStream outputStream = new FileOutputStream(file);
                originalBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();

                imagePath = file.getAbsolutePath();

                originalBitmap.recycle();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagePath;
    }

    private void updateUser(String userId, String name, String phone,String userEmail ,File imageFile) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(30, TimeUnit.SECONDS); // Thiết lập thời gian kết nối tối đa
        httpClient.readTimeout(30, TimeUnit.SECONDS); // Thiết lập thời gian đọc dữ liệu tối đa

        ApiServiceUser apiService = ApiClient.getClient().create(ApiServiceUser.class);

        RequestBody userEmailBody = RequestBody.create(MediaType.parse("text/plain"), userEmail);
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody phoneBody = RequestBody.create(MediaType.parse("text/plain"), phone);

        MultipartBody.Part imagePart = null;
        if (imageFile != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), imageBody);
        }

        Call<User> call = apiService.updateUser(userId, nameBody, phoneBody, userEmailBody, imagePart);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                hideWaitProgress();
                if (response.isSuccessful() && response.body() != null) {
                    User newUser = response.body();
                    Log.d("TAG", "onResponse: " + response.body());
                    SharedPrefsManager.clearUser(MydetailActivity.this);
                    SharedPrefsManager.saveUser(MydetailActivity.this, newUser);
                   CommonActivity.createAlertDialog(MydetailActivity.this,"Đổi thông tin thành công",
                           "Outstand'Food",new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        finish();
                                    }
                                }
                            ).show();
                } else {
                    show("Outsand'Food", "Lỗi cập nhật thông tin.");
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                hideWaitProgress();
                show("Outstand'Food", "Lỗi : " + t.getMessage());
            }
        });
    }


}
