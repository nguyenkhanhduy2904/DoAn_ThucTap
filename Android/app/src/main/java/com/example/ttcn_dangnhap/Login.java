package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText txt_tk;
    TextInputEditText txt_mk;
    Button btn_login;
    TextView txtSignup;

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
                                String role = data.getString("role");
                                int userid = data.getInt("id");
                                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("is_logged_in", true); // Lưu trạng thái đã đăng nhập
                                editor.putString("user_role", role);     // Lưu quyền (ADMIN/CUSTOMER) để sau này dùng
                                editor.putString("username", tenDangNhap); // Lưu tên đăng nhập (nếu cần hiển thị ở Infor)
                                editor.putInt("userid", userid );
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
                                        Intent intent = new Intent(Login.this, Voucher_admin.class);
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
    }



}