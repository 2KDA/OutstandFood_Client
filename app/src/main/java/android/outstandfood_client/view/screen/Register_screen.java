package android.outstandfood_client.view.screen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.outstandfood_client.R;


public class Register_screen extends AppCompatActivity {

    private EditText edtFullName, edtUserName, edtPassWord, edtPassWord1, edtPhone, edtEmail;
    private Button btnDangKy;
    private ImageView register_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        edtUserName = findViewById(R.id.inputUsername);
        edtFullName = findViewById(R.id.inputname);
        edtPhone = findViewById(R.id.inputPhone);
        edtEmail = findViewById(R.id.inputemail);
        edtPassWord = findViewById(R.id.inputPassword);
        edtPassWord1 = findViewById(R.id.inputPassword1);
        btnDangKy = findViewById(R.id.btnRegister);
        register_back = findViewById(R.id.register_back);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            // cần thay đổi đường dẫn wwifi mới chạy dc
            public void onClick(View view) {
                logup("http://192.168.2.44:3000/api/user/logup");
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
                    String email = edtEmail.getText().toString();
                    String fullname = edtFullName.getText().toString();

                    if (username.isEmpty() || password.isEmpty() || password1.isEmpty() || phone.isEmpty() || fullname.isEmpty() || email.isEmpty()) {
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
                    if (!isValidEmail(email)) {
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
                    jsonObject.put("userEmail", email);
                    jsonObject.put("role", "User");
                    jsonObject.put("isActive", true);
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

                        if (response.contains("Đăng ký thành công")) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Register_screen.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
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
                    }else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register_screen.this, "tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
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