package com.example.ttcn_dangnhap;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ttcn_dangnhap.Network.APIClient;
import com.example.ttcn_dangnhap.Network.APIService;
import com.example.ttcn_dangnhap.adapter.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

import models.APIResponse;
import models.OrderDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonHang extends AppCompatActivity {
    TextView tabTracking, tabHistory;
    RecyclerView rvOrders, rvHistory;
    List<OrderDTO> trackingList = new ArrayList<>();
    List<OrderDTO> historyList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_don_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
        loadUserOrders();
    }

    private void addEvents() {
        tabTracking.setOnClickListener(v -> {
            rvOrders.setVisibility(View.VISIBLE);
            rvHistory.setVisibility(View.GONE);
            tabTracking.setBackgroundColor(0xFF69E0D4);
            tabHistory.setBackgroundColor(0xFFB2DFDB);
        });

        tabHistory.setOnClickListener(v -> {
            rvOrders.setVisibility(View.GONE);
            rvHistory.setVisibility(View.VISIBLE);
            tabTracking.setBackgroundColor(0xFFB2DFDB);
            tabHistory.setBackgroundColor(0xFF69E0D4);
        });
    }

    private void addControls() {
        tabTracking = findViewById(R.id.tabTracking);
        tabHistory = findViewById(R.id.tabHistory);
        rvOrders = findViewById(R.id.rvOrders);
        rvHistory = findViewById(R.id.rvHistory);

        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
    }
    private void loadUserOrders() {
        int userId = sharedPreferences.getInt("userid", -1);
        APIService apiService = APIClient.getClient().create(APIService.class);

        // Gọi API qua Retrofit
        apiService.getOrdersByUser(userId).enqueue(new Callback<APIResponse<List<OrderDTO>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<OrderDTO>>> call, Response<APIResponse<List<OrderDTO>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Retrofit tự parse JSON vào listOrders
                    List<OrderDTO> allOrders = response.body().getData(); // Lấy data từ APIResponse

                    if (allOrders == null) allOrders = new ArrayList<>();

                    trackingList.clear();
                    historyList.clear();

                    for (OrderDTO order : allOrders) {
                        String status = order.getTrangThaiDonHang();
                        // Phân loại vào 2 tab dựa trên status
                        if ("Finish".equals(status) || "Cancel".equals(status) || "Refuse".equals(status)) {
                            historyList.add(order);
                        } else {
                            trackingList.add(order);
                        }
                    }

                    // Mode 2: USER_TRACKING
                    OrderAdapter trackingAdapter = new OrderAdapter(DonHang.this, trackingList, 2, null);
                    rvOrders.setAdapter(trackingAdapter);

                    // Mode 3: USER_HISTORY
                    OrderAdapter historyAdapter = new OrderAdapter(DonHang.this, historyList, 3, null);
                    rvHistory.setAdapter(historyAdapter);
                } else {
                    Toast.makeText(DonHang.this, "Không tải được dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<OrderDTO>>> call, Throwable t) {
                Toast.makeText(DonHang.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}