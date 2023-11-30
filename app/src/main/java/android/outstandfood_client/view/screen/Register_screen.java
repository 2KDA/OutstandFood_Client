package android.outstandfood_client.view.screen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.outstandfood_client.R;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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


public class Register_screen extends AppCompatActivity {
    private EditText edtFullName, edtUserName, edtPassWord, edtPassWord1,edtPhone,edtEmail;
    private Button btnDangKy;
    private ImageView register_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        edtUserName = findViewById(R.id.inputUsername);
        edtFullName = findViewById(R.id.inputname);
        edtPhone = findViewById(R.id.inputPhone);
        edtEmail = findViewById(R.id.inputEmail);
        edtPassWord = findViewById(R.id.inputPassword);
        edtPassWord1 = findViewById(R.id.inputPassword1);
        btnDangKy = findViewById(R.id.btnRegister);
        register_back = findViewById(R.id.register_back);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logup("http://10.0.2.2:3000/api/user/logup");
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
                    String mail = edtEmail.getText().toString();
                    String fullname = edtFullName.getText().toString();

                    if (username.isEmpty() || password.isEmpty() || password1.isEmpty() || phone.isEmpty() || fullname.isEmpty()|| mail.isEmpty()) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register_screen.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
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
                    if (!password.equals(password1)) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register_screen.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                    if (!isValidEmail(mail)) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register_screen.this, "Email không hợp lệ. Vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }


                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("username", username);
                    jsonObject.put("password", password);
                    jsonObject.put("passwd2", password1); // Đặt giá trị mật khẩu xác nhận
                    jsonObject.put("name", fullname);
                    jsonObject.put("phone", phone);

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
                        if (response.contains("đăng ký thành công")) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Register_screen.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if (response.contains("tài khoản đã tồn tại")) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Register_screen.this, "Tài khoản đã tồn tại. Vui lòng chọn tên đăng nhập khác", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Register_screen.this, "Lỗi đăng ký", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "0[1-9][0-9]{8}";
        return phoneNumber.matches(regex);
    }
    private boolean isValidEmail(String email) {
        String regex = "^(.+)@(.+)$";
        return email.matches(regex);
    }
}