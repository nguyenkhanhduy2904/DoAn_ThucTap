package com.example.ttcn_dangnhap;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;


public class Login extends AppCompatActivity {
    EditText txt_tk;
    TextInputEditText txt_mk;
    Button btn_login;
    TextView txtSignup, tvQuenPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addEvents() {
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Dangky.class);
                startActivity(intent);
            }
        });
        tvQuenPass.setOnClickListener(view -> {
            Toast.makeText(this, "Quen pass clicked", Toast.LENGTH_LONG).show();
            showForgotPasswordDialog();
        });

        btn_login.setOnClickListener(view -> {
            String tenDangNhap = txt_tk.getText().toString().trim();
            String matKhauRaw = txt_mk.getText().toString().trim();

            if(tenDangNhap.isEmpty() || matKhauRaw.isEmpty()){
                Toast.makeText(Login.this, "Vui lòng nhập đầy đủ tài khoản và mật khẩu!", Toast.LENGTH_SHORT).show();

                return;
            }

            String url = "http://10.0.2.2:8080/api/v1/login/login";

            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("tenDangNhap", tenDangNhap);
                jsonBody.put("matKhauRaw", matKhauRaw);
            }catch (Exception e){
                Toast.makeText(Login.this, e.getMessage(),Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            RequestQueue queue = Volley.newRequestQueue(this);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonBody,
                    response -> {
                        try {
                            String status = response.getString("status");
                            String message = response.getString("message");

                            if (status.equals("success")) {

                                JSONObject data = response.getJSONObject("data");
                                String Accstatus = data.getString("trangThai");
                                Log.d("Login", "AccSts: "+Accstatus);
                                if(Accstatus.equals("LOCKED")){
                                    Toast.makeText(this, "Tài khoản đã bị khóa", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                String role = data.getString("role");
                                int userid = data.getInt("id");
                                String address = data.getString("diaChi");
                                String phone = data.getString("sdt");
                                String username = data.getString("tenHienThi");
                                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("is_logged_in", true); // Lưu trạng thái đã đăng nhập
                                editor.putString("user_role", role);     // Lưu quyền (ADMIN/CUSTOMER) để sau này dùng
                                editor.putString("username", username);
                                editor.putInt("userid", userid );
                                editor.putString("userAddress",address );
                                editor.putString("userPhone", phone);

                                editor.apply(); // Xác nhận lưu

                                if (role.equals("CUSTOMER")) {
                                    ThongBao.showThongBao(Login.this, "Thành công", message, () -> {
                                        Intent intent = new Intent(Login.this, HomePage.class);
                                        startActivity(intent);
                                        finish();
                                    });
                                }
                                else if (role.equals("ADMIN")) {
                                    ThongBao.showThongBao(Login.this, "Thành công", message, () -> {
                                        Intent intent = new Intent(this, AdminOrder.class);
                                        startActivity(intent);
                                        finish();
                                    });
                                }
                                else {
                                    Toast.makeText(Login.this, "Role không hợp lệ!", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Toast.makeText(Login.this, "Login failed", Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(Login.this, "Error convert request: " + e.getMessage(),Toast.LENGTH_LONG ).show();

                        }
                    },
                    error -> {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                String errorJson = new String(error.networkResponse.data, "UTF-8");
                                JSONObject obj = new JSONObject(errorJson);

                                String status = obj.getString("status");
                                String message = obj.getString("message");

                                Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(Login.this, "Error parsing error response", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(Login.this, "Connection Error", Toast.LENGTH_LONG).show();
                        }
                    }
            );
            queue.add(request);
        });
    }

    private void addControls() {
        txt_tk=findViewById(R.id.txt_tk);
        txt_mk=findViewById(R.id.txt_mk);
        btn_login=findViewById(R.id.btn_login);
        txtSignup = findViewById(R.id.txt_dky);
        tvQuenPass = findViewById(R.id.txt_quenmk);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);
        if (isLoggedIn) {
            String role = sharedPreferences.getString("user_role", "CUSTOMER");

            if (role.equals("CUSTOMER")) {
                Intent intent = new Intent(Login.this, HomePage.class);
                startActivity(intent);
            } else if (role.equals("ADMIN")) {
                Intent intent = new Intent(Login.this, QuanLyMon.class);
                startActivity(intent);
            }
            finish();
        }
    }

    private void showForgotPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_forgot_password, null);
        builder.setView(dialogView);

        EditText edtLoginName = dialogView.findViewById(R.id.edtLoginName);
        EditText edtEmail = dialogView.findViewById(R.id.edtEmail);
        EditText edtPhone = dialogView.findViewById(R.id.edtPhone);
        EditText edtNewPass = dialogView.findViewById(R.id.edtNewPass);
        EditText edtConfirmPass = dialogView.findViewById(R.id.edtConfirmedPass);
        AppCompatButton btnConfirm = dialogView.findViewById(R.id.btnConfirm);

        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        btnConfirm.setOnClickListener(v -> {
            String loginName = edtLoginName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String newPass = edtNewPass.getText().toString().trim();
            String confirmPass = edtConfirmPass.getText().toString().trim();

            if(loginName.isEmpty() || email.isEmpty() || phone.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!newPass.equals(confirmPass)) {
                Toast.makeText(this, "Mật khẩu mới và xác nhận mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            // Build JSON body
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("sdt", phone);
                jsonBody.put("email", email);
                jsonBody.put("newPassword", newPass);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Lỗi tạo dữ liệu JSON", Toast.LENGTH_SHORT).show();
                return;
            }

            String url = "http://10.0.2.2:8080/api/v1/user/" + loginName + "/reset-password";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonBody,
                    response -> {
                        Toast.makeText(this, "Đặt lại mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    },
                    error -> {
                        Toast.makeText(this, "Lỗi: kiểm tra lại thông tin hoặc kết nối", Toast.LENGTH_SHORT).show();
                    });

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        });


        dialog.show();
    }


}