package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText txt_tk;
    TextInputEditText txt_mk;
    RadioButton rdo_user,rdo_admin;
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
                Intent intent = new Intent(Login.this, ttcn_dangky.class);
                startActivity(intent);
            }
        });


        btn_login.setOnClickListener( view -> {
            Intent intent = new Intent(Login.this, HomePage.class);
            startActivity(intent);

        });


//        btn_login.setOnClickListener(view -> {
//
//
//            String tk=txt_tk.getText().toString();
//            String mk=txt_mk.getText().toString();
//            String role = "";
//            if (rdo_user.isChecked()) {
//                role = "user";
//            } else if (rdo_admin.isChecked()) {
//                role = "admin";
//            }
//
//            if (tk.isEmpty() || mk.isEmpty()) {
//                Toast.makeText(Login.this, "Vui lòng nhập đầy đủ tài khoản và mật khẩu!", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (role.isEmpty()) {
//                Toast.makeText(Login.this, "Vui lòng chọn vai trò (User hoặc Admin)!", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            String url = "http://10.0.2.2:8080/login";
//            String finalRole = role;
//            RequestQueue queue = Volley.newRequestQueue(this);
//            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            try {
//                                JSONObject jsonObject = new JSONObject(response);
//                                String status = jsonObject.getString("status");
//                                String message = jsonObject.getString("message");
//                                if (status.equals("success")) {
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
//                                    builder.setTitle("Thông báo");
//                                    builder.setMessage(message);
//                                    builder.setPositiveButton("Ok",(dialogInterface, i) -> {});
//                                    builder.create().show();
//                                } else {
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
//                                    builder.setTitle("Thất bại");
//                                    builder.setMessage(message);
//                                    builder.setPositiveButton("Ok",(dialogInterface, i) -> {});
//                                    builder.create().show();
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                Toast.makeText(Login.this, "Lỗi đọc dữ liệu JSON", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(Login.this, "Lỗi kết nối Server!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//            ){
//                @Nullable
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("username",tk);
//                    params.put("password",mk);
//                    params.put("role", finalRole);
//                    return params;
//                }
//            };
//            queue.add(postRequest);
//            });
    }

    private void addControls() {
        txt_tk=findViewById(R.id.txt_tk);
        txt_mk=findViewById(R.id.txt_mk);
        btn_login=findViewById(R.id.btn_login);
        rdo_admin=findViewById(R.id.rdo_admin);
        rdo_user=findViewById(R.id.rdo_user);
        txtSignup = findViewById(R.id.txt_dky);


    }
}