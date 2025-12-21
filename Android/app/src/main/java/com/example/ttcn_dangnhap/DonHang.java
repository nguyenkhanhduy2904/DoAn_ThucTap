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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ttcn_dangnhap.Network.APICallback;
import com.example.ttcn_dangnhap.Network.APIClient;
import com.example.ttcn_dangnhap.Network.APIService;
import com.example.ttcn_dangnhap.Adapter.OrderAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import models.APIResponse;
import models.OrderDTO;
import models.OrderItemDTO;
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
//        loadUserOrders();


        loadThisUserOrders(new APICallback<List<OrderDTO>>() {
            @Override
            public void onSuccess(List<OrderDTO> result) {
                trackingList.clear();
                historyList.clear();

                for (OrderDTO order : result) {
                    String status = order.getTrangThaiDonHang();
                    // Phân loại vào 2 tab dựa trên status
                    if (status.equals("Finish") || status.equals("Cancel") || status.equals("Refuse")) {
                        historyList.add(order);
                    } else if(status.equals("Pending") || status.equals("Confirmed") || status.equals("In Transit")){
                        trackingList.add(order);
                    }
                }

                // Mode 2: USER_TRACKING
                OrderAdapter trackingAdapter = new OrderAdapter(DonHang.this, trackingList, 2, null);
                rvOrders.setAdapter(trackingAdapter);

                // Mode 3: USER_HISTORY
                OrderAdapter historyAdapter = new OrderAdapter(DonHang.this, historyList, 3, null);
                rvHistory.setAdapter(historyAdapter);
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(DonHang.this, "Lỗi tải đơn hàng: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
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
//        rvHistory = findViewById(R.id.rvHistory);

//        rvOrders.setLayoutManager(new LinearLayoutManager(this));
//        rvHistory.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
    }
//    private void loadUserOrders() {
//        int userId = sharedPreferences.getInt("userid", -1);
//        APIService apiService = APIClient.getClient().create(APIService.class);
//
//        // Gọi API qua Retrofit
//        apiService.getOrdersByUser(userId).enqueue(new Callback<APIResponse<List<OrderDTO>>>() {
//            @Override
//            public void onResponse(Call<APIResponse<List<OrderDTO>>> call, Response<APIResponse<List<OrderDTO>>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    // Retrofit tự parse JSON vào listOrders
//                    List<OrderDTO> allOrders = response.body().getData(); // Lấy data từ APIResponse
//
//                    if (allOrders == null) allOrders = new ArrayList<>();
//
//                    trackingList.clear();
//                    historyList.clear();
//
//                    for (OrderDTO order : allOrders) {
//                        String status = order.getTrangThaiDonHang();
//                        // Phân loại vào 2 tab dựa trên status
//                        if ("Finish".equals(status) || "Cancel".equals(status) || "Refuse".equals(status)) {
//                            historyList.add(order);
//                        } else {
//                            trackingList.add(order);
//                        }
//                    }
//
//                    // Mode 2: USER_TRACKING
//                    OrderAdapter trackingAdapter = new OrderAdapter(DonHang.this, trackingList, 2, null);
//                    rvOrders.setAdapter(trackingAdapter);
//
//                    // Mode 3: USER_HISTORY
//                    OrderAdapter historyAdapter = new OrderAdapter(DonHang.this, historyList, 3, null);
//                    rvHistory.setAdapter(historyAdapter);
//                } else {
//                    Toast.makeText(DonHang.this, "Không tải được dữ liệu", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<APIResponse<List<OrderDTO>>> call, Throwable t) {
//                Toast.makeText(DonHang.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void loadThisUserOrders(APICallback< List<OrderDTO>> callback) {
        int userID = sharedPreferences.getInt("userid",-1);
        if(userID==-1){
            callback.onError("User ID not found");
            return;
        }

        String url ="http://10.0.2.2:8080/api/v1/orders/userid/" + userID;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try{
                        String status = response.getString("status");
                        String message = response.getString("message");

                        if(status.equals("success")){
                            JSONArray dataArray = response.getJSONArray("data");
                            List<OrderDTO> orderList = new ArrayList<>();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                            for(int i =0; i< dataArray.length(); i++){

                                JSONObject orderObj = dataArray.getJSONObject(i);
                                String dateStr = orderObj.getString("thoiGianTao");
                                String tongTienStr = orderObj.getString("tongTien");

                                Date thoiGianTao = null;
                                BigDecimal tongTien = null;


                                try{
                                    thoiGianTao = sdf.parse(dateStr);
                                    tongTien = new BigDecimal(tongTienStr);
                                }catch (Exception e){
                                    Toast.makeText(DonHang.this, "Lỗi chuyển đổi dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    return;
                                }


                                OrderDTO order = new OrderDTO(
                                        orderObj.getInt("id"),
                                        orderObj.getString("tenNguoiNhan"),
                                        orderObj.getString("diaChi"),
                                        orderObj.getString("sdt"),
                                        thoiGianTao,
                                        orderObj.getString("trangThaiDonHang"),
                                        orderObj.getString("trangThaiThanhToan"),
                                        tongTien,
                                        orderObj.getInt("idKhachHang"),
                                        null // Chưa xử lý danh sách món ăn ở đây
                                );


                                JSONArray itemList = orderObj.getJSONArray("items");
                                List<OrderItemDTO> orderItems = new ArrayList<>();
                                for(int j=0; j< itemList.length(); j++){
                                    JSONObject itemObj = dataArray.getJSONObject(j);
                                    String giaTungMonStr = itemObj.getString("giaTungMon");
                                    String giaTongMonStr = itemObj.getString("giaTongMon");
                                    BigDecimal giaTungMon = null;
                                    BigDecimal giaTongMon = null;


                                    try{
                                        giaTungMon = new BigDecimal(giaTungMonStr);
                                        giaTongMon = new BigDecimal(giaTongMonStr);
                                    }
                                    catch (Exception e){
                                        Toast.makeText(DonHang.this, "Lỗi chuyển đổi dữ liệu món ăn: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    OrderItemDTO item = new OrderItemDTO(
                                            itemObj.getString("tenMon"),
                                            itemObj.getInt("soLuong"),
                                            itemObj.getString("ghiChu"),
                                            giaTungMon,
                                            giaTongMon,
                                            itemObj.getInt("monanid")
                                    );

                                    orderItems.add(item);


                                }
                                order.setItems(orderItems);
                                orderList.add(order);

                            }
                            callback.onSuccess(orderList);

                        }
                        else{
                            callback.onError("GET order data failed: " +message);
                        }

                    }catch (Exception e){
                        Toast.makeText(DonHang.this, "Lỗi phân tích dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                },
                error -> {
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

        requestQueue.add(request);
    }
}