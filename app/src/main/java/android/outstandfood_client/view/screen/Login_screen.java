package android.outstandfood_client.view.screen;

import android.content.Intent;
import android.os.Bundle;
import android.outstandfood_client.R;
import androidx.appcompat.app.AppCompatActivity;
import android.outstandfood_client.interfaceApi.ApiServiceUser;
import android.outstandfood_client.models.User;
import android.outstandfood_client.view.screen.home_action_menu.Home_Screen;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Login_screen extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button btn_dangnhap;
    private TextView textViewSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        username = findViewById(R.id.edt_Login_User);
        password = findViewById(R.id.edt_Login_Password);
        btn_dangnhap = findViewById(R.id.btnLoginEmail);
        textViewSignUp = findViewById(R.id.textViewSignUp);
        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();
                login(user, pass);

            }
        });
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_screen.this, Register_screen.class);
                startActivity(intent);
            }
        });
    }

    private void login(String username, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.119:3000/api/") // Thay thế bằng URL của API thực tế
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServiceUser apiService = retrofit.create(ApiServiceUser.class);

        User loginRequest = new User();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        Call<User> call = apiService.login(loginRequest);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User userResponse = response.body();

                    Intent intent = new Intent(Login_screen.this, Home_Screen.class);
                    Toast.makeText(Login_screen.this, " " + userResponse.get_id(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                } else {


                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Xử lý khi gọi API thất bại
            }
        });

    }


}