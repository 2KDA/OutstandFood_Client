package android.outstandfood_client.view.screen;

import android.outstandfood_client.R;

import androidx.appcompat.app.AppCompatActivity;


import android.outstandfood_client.interfaceApi.ApiServiceUser;

import android.outstandfood_client.models.User;
import android.outstandfood_client.object.SharedPrefsManager;
import android.outstandfood_client.view.screen.home_action_menu.Home_Screen;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;



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
        btn_dangnhap = findViewById(R.id.btn_Login);
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
                    SharedPrefsManager.saveUser(Login_screen.this, userResponse);
                    Intent intent = new Intent(Login_screen.this, Home_Screen.class);
                    Toast.makeText(Login_screen.this, " " + userResponse.get_id(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                } else {


                    http.setRequestProperty("Content-Type", "application/json");
                    //Tạo đối tượng out dữ liệu ra khỏi ứng dụng để gửi lên server
                    OutputStream outputStream = http.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                    bufferedWriter.append(jsonObject.toString());
                    //Xóa bộ đệm
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    //Nhận lại dữ liệu phản hồi
                    int responseCode = http.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Xử lý dữ liệu phản hồi từ server
                        InputStream inputStream = http.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            response.append(line);
                        }
                        bufferedReader.close();
                        inputStream.close();

                        // Giải mã dữ liệu JSON phản hồi từ server để lấy thông tin người dùng
                        JSONObject responseJson = new JSONObject(response.toString());
                        String userId = responseJson.optString("userId");
                        String returnedUsername = responseJson.optString("username");
                        String returnedPassword = responseJson.optString("password");
                        String returnedRole= responseJson.optString("role");
                        String returnedemailUser= responseJson.optString("emailUser");
                        String returnedFullname= responseJson.optString("name");
                        String returnedimage= responseJson.optString("image");
                        String returnedphone= responseJson.optString("phone");
                        boolean returnedisActive= responseJson.getBoolean("isActive");


                        // Tạo đối tượng UserData để truyền sang màn hình Home
                        User user = new User(returnedUsername, returnedPassword, userId,
                                returnedFullname,returnedRole,returnedemailUser,returnedimage,returnedphone,returnedisActive);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Login_screen.this, "Chào mừng " + returnedUsername + " đã đến với thế giới đồ ăn", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login_screen.this, Home_Screen.class);
                                intent.putExtra("userData", user);
                                startActivity(intent);
                                finish(); // Đóng màn hình đăng nhập
                            }
                        });
                    } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST ) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Login_screen.this, "Thông tin tài khoản có sự nhầm lẫn", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED ) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Login_screen.this, "Thông tin mật khẩu có sự nhầm lẫn", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Login_screen.this, "Tài khoản của bạn đã bị khóa", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Login_screen.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Xử lý khi gọi API thất bại
            }
        });

    }


}