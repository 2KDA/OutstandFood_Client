package android.outstandfood_client.view.screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.outstandfood_client.OutstandActivity;
import android.outstandfood_client.R;
import android.outstandfood_client.object.CommonActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

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


public class Register_screen extends OutstandActivity {
    private EditText edtFullName, edtUserName, edtPassWord, edtPassWord1, edtPhone, edtEmail;
    private Button btnDangKy;
    private ImageView register_back;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        edtUserName = findViewById(R.id.inputUsername);
        edtFullName = findViewById(R.id.inputname);
        edtPhone = findViewById(R.id.inputPhone);
        edtPassWord = findViewById(R.id.inputPassword);
        edtPassWord1 = findViewById(R.id.inputPassword1);
        edtEmail = findViewById(R.id.inputEmail);
        btnDangKy = findViewById(R.id.btnRegister);
        register_back = findViewById(R.id.register_back);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(Register_screen.this);
                progressDialog.setMessage("Vui lòng đợi...");
                progressDialog.show();

                logup("https://outstanfood-com.onrender.com/api/user/logup");
            }
        });

        register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void logup(String link) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        String urlLink = link;

        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlLink);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setRequestMethod("POST");

                    String username = edtUserName.getText().toString();
                    String password = edtPassWord.getText().toString();
                    String password1 = edtPassWord1.getText().toString();
                    String phone = edtPhone.getText().toString();
                    String Email = edtEmail.getText().toString();
                    String fullname = edtFullName.getText().toString();

                    if (username.isEmpty() || password.isEmpty() || password1.isEmpty() || phone.isEmpty() || fullname.isEmpty() || Email.isEmpty()) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register_screen.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                    if (username.length() <= 6  ) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register_screen.this, "Độ dài của tài khoản phải trên 6", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                    if (fullname.length() <=10 ) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register_screen.this, "Độ dài của họ và tên phải trên 10", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                    if (!isValidPhoneNumber(phone)) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register_screen.this, "Số điện thoại không hợp lệ. Vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                    if (!isValidEmail(Email)) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register_screen.this, "Email không hợp lệ. Vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                    if (!password.equals(password1)) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register_screen.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }


                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("username", username);
                    jsonObject.put("password", password);
                    jsonObject.put("passwd2", password1); // Đặt giá trị mật khẩu xác nhận
                    jsonObject.put("name", fullname);
                    jsonObject.put("userEmail", Email);
                    jsonObject.put("phone", phone);
                    FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w("TAG", "Failed to get FCM registration token", task.getException());
                                return;
                            }
                            String deviceToken = task.getResult();

                            try {
                                jsonObject.put("deviceToken", deviceToken);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

                    http.setRequestProperty("Content-Type", "application/json");
                    OutputStream outputStream = http.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                    bufferedWriter.append(jsonObject.toString());
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    int responseCode = http.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = http.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder builder = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line).append("\n");
                        }
                        reader.close();
                        inputStream.close();
                        http.disconnect();

                        String response = builder.toString();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                CommonActivity.createAlertDialog(Register_screen.this,"Đăng kí thành công.", "Outstand'Food",
                                v ->{
                                    startActivity(new Intent(Register_screen.this, Login_screen.class));
                                }
                                ).show();
                            }
                        });
                    } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(Register_screen.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
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
                progressDialog.dismiss();
            }
        });
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "0[1-9][0-9]{8}";
        return phoneNumber.matches(regex);
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(regex);
    }

}