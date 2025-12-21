package com.example.ttcn_dangnhap;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ttcn_dangnhap.Network.APIClient;
import com.example.ttcn_dangnhap.Network.APIService;
import com.example.ttcn_dangnhap.adapter.OrderAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import models.APIResponse;
import models.OrderDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminOrder extends AppCompatActivity {
    AppCompatButton btnCho, btnXacNhan, btnSanSang, btnDaGiao;
    RecyclerView rvOrderList;
    List<OrderDTO> allOrders = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addControls() {
        btnCho=findViewById(R.id.btnCho);
        btnSanSang=findViewById(R.id.btnSanSang);
        btnXacNhan=findViewById(R.id.btnXacNhan);
        btnDaGiao=findViewById(R.id.btnDaGiao);
        rvOrderList = findViewById(R.id.rvOrderList);
        rvOrderList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAllOrders();
    }

    private void addEvents() {
        btnCho.setOnClickListener(v -> filterList("Pending"));
        btnXacNhan.setOnClickListener(v -> filterList("Confirm"));
        btnSanSang.setOnClickListener(v -> filterList("Delivering"));
        btnDaGiao.setOnClickListener(v -> filterList("Finish"));
    }
    private void loadAllOrders() {
        APIService apiService = APIClient.getClient().create(APIService.class);

        apiService.getAllOrders().enqueue(new Callback<APIResponse<List<OrderDTO>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<OrderDTO>>> call, Response<APIResponse<List<OrderDTO>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allOrders = response.body().getData();
                    if (allOrders == null) allOrders = new ArrayList<>();
                    filterList("Pending");
                } else {
                    Toast.makeText(AdminOrder.this, "Lỗi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<APIResponse<List<OrderDTO>>> call, Throwable t) {
                Toast.makeText(AdminOrder.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void filterList(String status) {
        List<OrderDTO> filtered = new ArrayList<>();
        if (allOrders != null) {
            for (OrderDTO order : allOrders) {
                if (status.equalsIgnoreCase(order.getTrangThaiDonHang())) {
                    filtered.add(order);
                }
            }
        }
        OrderAdapter adapter = new OrderAdapter(this, filtered, 1, this::updateOrderStatus);
        rvOrderList.setAdapter(adapter);
    }
    private void updateOrderStatus(int orderId, String newStatus) {
        String url = "http://10.0.2.2:8080/api/v1/orders/" + orderId + "/status?status=" + newStatus;

        StringRequest request = new StringRequest(Request.Method.PUT, url,
                response -> {
                    Toast.makeText(this, "Đã cập nhật: " + newStatus, Toast.LENGTH_SHORT).show();
                    loadAllOrders();
                },
                error -> Toast.makeText(this, "Lỗi cập nhật", Toast.LENGTH_SHORT).show()
        );
        Volley.newRequestQueue(this).add(request);
    }
}