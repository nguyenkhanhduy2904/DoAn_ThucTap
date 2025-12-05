package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class forgot extends AppCompatActivity {
    TextView txt_back;
    Button btn_gui;
    EditText txt_sdt_recover;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addEvents() {
        txt_back.setOnClickListener(view -> finish());
        btn_gui.setOnClickListener(view -> {
            String sdt=txt_sdt_recover.getText().toString();
            if (sdt.isEmpty())
            {
                txt_sdt_recover.setError("Vui lòng nhập số điện thoại!");
                return;
            }
            else
            {
                resetMk(sdt);
            }
        });
    }

    private void addControls() {
        txt_back= findViewById(R.id.txt_back);
        txt_sdt_recover=findViewById(R.id.txt_sdt_recover);
        btn_gui=findViewById(R.id.btn_gui);
    }
    private void resetMk(String sdt)
    {
        String url = "http://10.0.2.2:8080/forgot";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            if (status.equals("success")) {
                                ThongBao.showThongBao(forgot.this,"Thông báo",message,null);
                            } else {
                                ThongBao.showThongBao(forgot.this,"Thất bại",message,null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(forgot.this, "Lỗi đọc dữ liệu JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(forgot.this, "Lỗi kết nối Server!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sdt",sdt);
                return params;
            }
        };
        queue.add(postRequest);
    }
}