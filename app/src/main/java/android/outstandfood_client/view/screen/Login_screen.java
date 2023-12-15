package android.outstandfood_client.view.screen;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.outstandfood_client.OutstandActivity;
import android.outstandfood_client.R;

import androidx.appcompat.app.AppCompatActivity;

import android.outstandfood_client.R;
import android.outstandfood_client.Utils;
import android.outstandfood_client.models.User;
import android.outstandfood_client.object.SharedPrefsManager;
import android.outstandfood_client.view.screen.home_action_menu.Home_Screen;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Login_screen extends OutstandActivity {
    private EditText username;
    private EditText password;
    private Button btn_dangnhap;
    private TextView textViewSignUp;
    private ProgressDialog progressDialog;
    private ImageView login_logo;
    private ImageView login_exit;
    public static final String URL_SERVER = "https://outstanfood-com.onrender.com/api/" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        username = findViewById(R.id.edt_Login_User);
        password = findViewById(R.id.edt_Login_Password);
        btn_dangnhap = findViewById(R.id.btnLoginEmail);
        textViewSignUp = findViewById(R.id.textViewSignUp);
        login_logo = findViewById(R.id.login_logo);
        login_exit = findViewById(R.id.login_exit);

        RequestOptions requestOptions = new RequestOptions().transform(new CircleCrop());
        Glide.with(this)
                .load(R.drawable.logo_splash)
                .apply(requestOptions)
                .into(login_logo);


        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString();
                String passWord = password.getText().toString();
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord)) {
                    show("Outsand'Food", "Tài khoản và mật khẩu không được để trống.");
                } else {
                    progressDialog = new ProgressDialog(Login_screen.this);
                    progressDialog.setMessage("Đang đăng nhập...");
                    progressDialog.show();
                    login(URL_SERVER + "user/login");
                }
            }
        });
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_screen.this, Register_screen.class);
                startActivity(intent);
                finish();
            }
        });
        login_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void login(String link) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        String urlLink = link;

        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlLink);
                    //mã kết nối
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setRequestMethod("POST");
                    //Tạo đối tượng dữ liệu gửi lên server
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("username", username.getText().toString());
                    jsonObject.put("password", password.getText().toString());


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
                        JSONObject userJson = responseJson.getJSONObject("user");


                        String userId = userJson.optString("_id");
                        String returnedUsername = userJson.optString("username");
                        String returnedPassword = userJson.optString("password");
                        String returnedRole= userJson.optString("role");
                        String returnedFullname= userJson.optString("name");
                        String userEmail= userJson.optString("userEmail");
                        String returnedimage= userJson.optString("image");
                        String returnedphone= userJson.optString("phone");
                        boolean returnedisActive= userJson.getBoolean("isActive");

                        // Tạo đối tượng UserData để truyền sang màn hình Home
                        User user = new User(userId,returnedUsername,returnedFullname,
                                returnedPassword,returnedRole,userEmail, returnedimage,returnedphone,returnedisActive);

                        SharedPrefsManager.saveUser(Login_screen.this, user);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Utils.showCustomToast(Login_screen.this,"Thanh cong");
                                Intent intent = new Intent(Login_screen.this, Home_Screen.class);
                                startActivity(intent);
                                finish(); // Đóng màn hình đăng nhập
                                progressDialog.dismiss();
                            }
                        });
                    } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST ) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                show("Outsand'Food", "Tài khoản không tồn tại.");
                            }
                        });
                    }else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED ) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                show("Outsand'Food", "Mật khẩu không chính xác.");
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
                    Log.d("TAG", "run: " + e.getMessage());
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    Log.d("TAG", "run: " + e.getMessage());
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    Log.d("TAG", "run: " + e.getMessage());
                    throw new RuntimeException(e);
                }finally {
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login_screen.this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setCancelable(false)
                .show();
    }


}