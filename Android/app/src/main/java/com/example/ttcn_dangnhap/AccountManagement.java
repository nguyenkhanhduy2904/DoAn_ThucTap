package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import com.example.ttcn_dangnhap.Network.APICallback;
import com.example.ttcn_dangnhap.adapter.AccountManagementAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import models.EQuocGia;
import models.MonAn;
import models.UserDTO;

public class AccountManagement extends AppCompatActivity {

    List<UserDTO> lsUserDTO;
    AccountManagementAdapter adapter;
    ListView lv;


    LinearLayout ibtnFoodManagement, ibtnOrder, ibtnAccountManagement, ibtnYourAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lsUserDTO = new ArrayList<>();

        addControls();
        addEvents();

        getUserDTOList(new APICallback<List<UserDTO>>(){
            @Override
            public void onSuccess(List<UserDTO> result) {
                lsUserDTO.clear();

                lsUserDTO.addAll(result);
                adapter.notifyDataSetChanged();
                Toast.makeText(AccountManagement.this, "Success fetch" + lsUserDTO.size() + "items", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(AccountManagement.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });

    }

    void addControls(){
        ibtnYourAccount = findViewById(R.id.ibtnMyAccountAdmin);
        ibtnOrder = findViewById(R.id.ibtnOrderAdmin);
        ibtnAccountManagement = findViewById(R.id.ibtnAccountManagement);
        ibtnFoodManagement = findViewById(R.id.ibtnFoodManagementAdmin);

        lv=findViewById(R.id.lvUser);
        adapter = new AccountManagementAdapter(this, lsUserDTO );
        lv.setAdapter(adapter);
    }

    void addEvents(){
        ibtnYourAccount.setOnClickListener(view -> {
            Intent intent = new Intent(this, Infor.class);
            startActivity(intent);
        });

        ibtnOrder.setOnClickListener(view -> {
            Intent intent = new Intent(this, AdminOrder.class);
            startActivity(intent);
        });
        ibtnFoodManagement.setOnClickListener(view -> {
            Intent intent = new Intent(this, QuanLyMon.class);
            startActivity(intent);
        });
        ibtnAccountManagement.setOnClickListener(view -> {
            Intent intent = new Intent(this, AccountManagement.class);
            startActivity(intent);
            finish();
        });
    }

    void getUserDTOList(APICallback<List<UserDTO>> callback){
        String url = "http://10.0.2.2:8080/api/v1/user";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        String status = response.getString("status");
                        String message = response.getString("message");
                        if(status.equals("success")){
                            JSONArray dataArray = response.getJSONArray("data");
                            List<UserDTO> resultList = new ArrayList<>();
                            for(int i =0; i<dataArray.length(); i++){
                                JSONObject obj = dataArray.getJSONObject(i);

                                UserDTO userDTO = new UserDTO(
                                        obj.getInt("id"),
                                        obj.getString( "tenHienThi"),
                                        obj.getString("sdt"),
                                        obj.getString("role"),
                                        obj.getString( "diaChi"),
                                        obj.getString("gioiTinh"),
                                        obj.getString("trangThai"),
                                        obj.getString( "email")

                                );

                                resultList.add(userDTO);
                            }

                            callback.onSuccess(resultList);

                        }else{
                            callback.onError("GET food data failed: " + message);
                        }

                    }catch (Exception e){
                        callback.onError("Error converting request: " + e.getMessage());
                    }

                },error -> {
            if (error.networkResponse != null && error.networkResponse.data != null) {
                try {
                    String errorJson = new String(error.networkResponse.data, "UTF-8");
                    JSONObject obj = new JSONObject(errorJson);

                    callback.onError(obj.getString("message"));

                } catch (Exception e) {
                    callback.onError("Error parsing error response: " + e.getMessage());
                }
            } else {
                callback.onError("Connection error");
            }
        }
        );

        queue.add(request);

    }


}