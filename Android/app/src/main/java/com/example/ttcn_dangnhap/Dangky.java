package com.example.ttcn_dangnhap;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

public class Dangky extends AppCompatActivity {

    EditText eTxTFullName, eTxTPhoneNum,eTxTEmail,eTxTAddress, eTxTUserLoginName,eTxTPassword,eTxTRepeatPassword;
    Spinner spinnerGender;
    MaterialButton mBtnCreateAcc;
    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ttcn_dangky);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }


    void addControls(){
        eTxTFullName = findViewById(R.id.eTxTFullName);
        eTxTAddress = findViewById(R.id.eTxTAddress);
        eTxTPhoneNum= findViewById(R.id.eTxTPhoneNum);
        eTxTEmail = findViewById(R.id.eTxTEmail);
        eTxTUserLoginName = findViewById(R.id.eTxTUserLoginName);
        eTxTPassword = findViewById(R.id.eTxTPassword);
        eTxTRepeatPassword = findViewById(R.id.eTxTRepeatPassword);
        mBtnCreateAcc = findViewById(R.id.mBtnCreateAcc);
        spinnerGender= findViewById(R.id.spinner_gender);
        ivBack = findViewById(R.id.ivBack);



    }

    void addEvents(){
        //to be implements
        ivBack.setOnClickListener(view -> {
            finish();
        });

        mBtnCreateAcc.setOnClickListener(view -> {

            String TenHienThi = eTxTFullName.getText().toString().trim();
            String DiaChi = eTxTAddress.getText().toString().trim();

            if (spinnerGender.getSelectedItem() == null) {
                Toast.makeText(this, "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
                return;
            }

            String GioiTinh = spinnerGender.getSelectedItem().toString();

            String SDT = eTxTPhoneNum.getText().toString().trim();
            String Email = eTxTEmail.getText().toString().trim();
            String TenDangNhap = eTxTUserLoginName.getText().toString().trim();
            String MatKhau = eTxTPassword.getText().toString().trim();
            String XacNhanMatKhau = eTxTRepeatPassword.getText().toString().trim();



            if(TenHienThi.isBlank()){
                Toast.makeText(Dangky.this, "Vui lòng nhập tên của bạn",Toast.LENGTH_LONG).show();
                return;
            }
            if(SDT.isBlank()){
                Toast.makeText(Dangky.this, "Vui lòng nhập số điện thoai của bạn",Toast.LENGTH_LONG).show();
                return;
            }
            if(Email.isBlank()){
                Toast.makeText(Dangky.this, "Vui lòng nhập email của bạn",Toast.LENGTH_LONG).show();
                return;
            }
            if(TenDangNhap.isBlank()){
                Toast.makeText(Dangky.this, "Vui lòng nhập Tên đăng nhập",Toast.LENGTH_LONG).show();
                return;
            }
            if(MatKhau.isBlank()){
                Toast.makeText(Dangky.this, "Vui lòng nhập Mật Khẩu",Toast.LENGTH_LONG).show();
                return;
            }


            if(!MatKhau.equals(XacNhanMatKhau)){
                Toast.makeText(Dangky.this, "Xác nhận mật khẩu không trùng khớp",Toast.LENGTH_LONG).show();
                return;
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                Toast.makeText(Dangky.this, "Email không hợp lệ",Toast.LENGTH_LONG).show();
                return;

            }

            if (!SDT.matches("^(0[3|5|7|8|9][0-9]{8}|\\+84[3|5|7|8|9][0-9]{8})$")) {//regex check format sdt(?)
                Toast.makeText(Dangky.this, "Số điện thoại không hợp lệ!", Toast.LENGTH_LONG).show();
                return;
            }

            String url ="http://10.0.2.2:8080/api/v1/user";

            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("tenDangNhap", TenDangNhap);
                jsonBody.put("matKhau", MatKhau);
                jsonBody.put("role", "CUSTOMER");
                jsonBody.put("sdt", SDT);
                jsonBody.put("diaChi", DiaChi);
                jsonBody.put("tenHienThi", TenHienThi);
                jsonBody.put("gioiTinh",GioiTinh);
                jsonBody.put("trangThai", "ACTIVE");
                jsonBody.put("email", Email);

            }
            catch (Exception e){
                Toast.makeText(Dangky.this, e.getMessage(),Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            RequestQueue queue = Volley.newRequestQueue(this);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonBody,
                    response -> {
                        try{
                            String status = response.getString("status");
                            String message = response.getString("message");
                            if(status.equals("success")){
                                Toast.makeText(Dangky.this, "Create Account Success", Toast.LENGTH_LONG).show();
                                //intent chuyen activity

                            }else{
                                Toast.makeText(Dangky.this, "Create Account failed: " + message, Toast.LENGTH_LONG).show();
                            }

                        }
                        catch (Exception e){
                            Toast.makeText(Dangky.this, "Error convert request: " + e.getMessage(),Toast.LENGTH_LONG ).show();
                        }

                    },
                    error -> {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                String errorJson = new String(error.networkResponse.data, "UTF-8");
                                JSONObject obj = new JSONObject(errorJson);

                                String status = obj.getString("status");
                                String message = obj.getString("message");

                                Toast.makeText(Dangky.this, message, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(Dangky.this, "Error parsing error response", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(Dangky.this, "Connection Error", Toast.LENGTH_LONG).show();
                        }
                    }
            );

            queue.add(request);
        });

    }
}