package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ttcn_dangnhap.Network.APICallback;
import com.example.ttcn_dangnhap.adapter.OrderStaffviewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import models.OrderDTO;
import models.OrderItemDTO;

public class AdminOrder extends AppCompatActivity {
    AppCompatButton btnPending, btnConfirmed, btnDelivering, btnFinished, btnCancelled;
    ListView lvOrders;

    LinearLayout ibtnFoodManagement, ibtnOrder, ibtnAccountManagement, ibtnYourAccount;


    OrderStaffviewAdapter orderAdapter;
    List<OrderDTO> allOrders = new ArrayList<>();
    List<OrderDTO> pendingOrders = new ArrayList<>();
    List<OrderDTO> confirmOrders = new ArrayList<>();
    List<OrderDTO> deliveringOrders = new ArrayList<>();
    List<OrderDTO> finishedOrders = new ArrayList<>();
    List<OrderDTO> canceled_and_refused_Orders = new ArrayList<>();

    List<OrderDTO> currentViewOrders = new ArrayList<>();

    String currentFilterStatus = "Pending"; // track which tab is active

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable autoReloadRunnable;
    private static final long REFRESH_INTERVAL = 5000;

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

        autoReloadRunnable = new Runnable() {
            @Override
            public void run() {
                loadAllOrders(new APICallback<List<OrderDTO>>() {
                    @Override
                    public void onSuccess(List<OrderDTO> result) {
                        allOrders.clear();
                        allOrders.addAll(result);
//                        orderAdapter.notifyDataSetChanged(); // <--- update adapter


                        pendingOrders.clear();
                        confirmOrders.clear();
                        deliveringOrders.clear();
                        finishedOrders.clear();
                        canceled_and_refused_Orders.clear();


                        for(int i =0; i<allOrders.size(); i++){
                            OrderDTO orderDTO = allOrders.get(i);
                            String orderStatus = orderDTO.getTrangThaiDonHang();

                            switch (orderStatus){
                                case "Pending":
                                    pendingOrders.add(orderDTO);
                                    break;
                                case "Confirmed":
                                    confirmOrders.add(orderDTO);
                                    break;
                                case "Delivering":
                                    deliveringOrders.add(orderDTO);
                                    break;
                                case "Finished":
                                    finishedOrders.add(orderDTO);
                                    break;

                                case "Cancelled":
                                case "Refused":
                                    canceled_and_refused_Orders.add(orderDTO);
                                    break;
                                default:
                                    break;
                            }
                        }

                        switchTab(currentFilterStatus);

                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(AdminOrder.this, "Lỗi tải dữ liệu: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });

                // Schedule next run
                handler.postDelayed(this, REFRESH_INTERVAL);
            }
        };

        addEvents();
        handler.post(autoReloadRunnable);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(autoReloadRunnable);
    }

    private void addControls() {
        btnPending = findViewById(R.id.btnPending);
        btnDelivering = findViewById(R.id.btnDelivering);
        btnConfirmed = findViewById(R.id.btnConfirm);
        btnFinished = findViewById(R.id.btnFinish);
        btnCancelled= findViewById(R.id.btnCanceled);

        lvOrders = findViewById(R.id.lvOrders);

        ibtnYourAccount = findViewById(R.id.ibtnMyAccountAdmin);
        ibtnOrder = findViewById(R.id.ibtnOrderAdmin);
        ibtnAccountManagement = findViewById(R.id.ibtnAccountManagement);
        ibtnFoodManagement = findViewById(R.id.ibtnFoodManagementAdmin);




        orderAdapter = new OrderStaffviewAdapter(this, currentViewOrders);
        orderAdapter.setStatusChangeCallback(new APICallback<OrderDTO>() {
            @Override
            public void onSuccess(OrderDTO updatedOrder) {
                // Remove from old lists
                pendingOrders.remove(updatedOrder);
                confirmOrders.remove(updatedOrder);
                deliveringOrders.remove(updatedOrder);
                finishedOrders.remove(updatedOrder);
                canceled_and_refused_Orders.remove(updatedOrder);

                // Update allOrders
                for (int i = 0; i < allOrders.size(); i++) {
                    if (allOrders.get(i).getId() == updatedOrder.getId()) {
                        allOrders.set(i, updatedOrder);
                        break;
                    }
                }

                // Add to new status list
                switch (updatedOrder.getTrangThaiDonHang()) {
                    case "Pending": pendingOrders.add(updatedOrder); break;
                    case "Confirmed": confirmOrders.add(updatedOrder); break;
                    case "Delivering": deliveringOrders.add(updatedOrder); break;
                    case "Finished": finishedOrders.add(updatedOrder); break;
                    case "Cancelled": canceled_and_refused_Orders.add(updatedOrder); break;
                }

                // Refresh current view
                switchTab(currentFilterStatus);
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(AdminOrder.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        lvOrders.setAdapter(orderAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
//        loadAllOrders();
    }

    private void switchTab(String status) {
        currentFilterStatus = status;
        currentViewOrders.clear();

        switch (status) {
            case "Pending": currentViewOrders.addAll(pendingOrders); break;
            case "Confirmed": currentViewOrders.addAll(confirmOrders); break;
            case "Delivering": currentViewOrders.addAll(deliveringOrders); break;
            case "Finished": currentViewOrders.addAll(finishedOrders); break;
            case "Cancelled": currentViewOrders.addAll(canceled_and_refused_Orders); break;
        }

        orderAdapter.notifyDataSetChanged();
    }


    private void addEvents() {
        btnPending.setOnClickListener(v -> switchTab("Pending"));
        btnConfirmed.setOnClickListener(v -> switchTab("Confirmed"));
        btnDelivering.setOnClickListener(v -> switchTab("Delivering"));
        btnFinished.setOnClickListener(v -> switchTab("Finished"));
        btnCancelled.setOnClickListener(v -> switchTab("Cancelled"));


        ibtnYourAccount.setOnClickListener(view -> {
            Intent intent = new Intent(this, Infor.class);
            startActivity(intent);
            finish();
        });

        ibtnOrder.setOnClickListener(view -> {
            Intent intent = new Intent(this, AdminOrder.class);
            startActivity(intent);
            finish();
        });
        ibtnFoodManagement.setOnClickListener(view -> {
            Intent intent = new Intent(this, QuanLyMon.class);
            startActivity(intent);
            finish();
        });

        ibtnAccountManagement.setOnClickListener(view -> {
            Intent intent = new Intent(this, AccountManagement.class);
            startActivity(intent);
            finish();
        });



    }



    public void loadAllOrders(APICallback< List<OrderDTO>> callback){

        String url = "http://10.0.2.2:8080/api/v1/orders";

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
                                    Toast.makeText(this, "Lỗi chuyển đổi dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                                        orderObj.getString("phuongThucThanhToan"),
                                        null // Chưa xử lý danh sách món ăn ở đây
                                );


                                JSONArray itemList = orderObj.getJSONArray("items");
                                List<OrderItemDTO> orderItems = new ArrayList<>();
                                for(int j=0; j< itemList.length(); j++){
                                    JSONObject itemObj = itemList.getJSONObject(j);
                                    String giaTungMonStr = itemObj.getString("giaTungMon");
                                    String giaTongMonStr = itemObj.getString("giaTongMon");
                                    BigDecimal giaTungMon = null;
                                    BigDecimal giaTongMon = null;


                                    try{
                                        giaTungMon = new BigDecimal(giaTungMonStr);
                                        giaTongMon = new BigDecimal(giaTongMonStr);
                                    }
                                    catch (Exception e){
                                        Toast.makeText(this, "Lỗi chuyển đổi dữ liệu món ăn: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                            orderList.sort((o1,o2)-> o2.getThoiGianTao().compareTo(o1.getThoiGianTao()));
                            callback.onSuccess(orderList);

                        }
                        else{
                            callback.onError("GET order data failed: " +message);
                        }

                    }catch (Exception e){
                        Toast.makeText(this, "Lỗi phân tích dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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




    private void updateOrderStatus(int orderId, String newStatus) {
        String url = "http://10.0.2.2:8080/api/v1/orders/update-order-status/" + orderId + "?status=" + newStatus;


        StringRequest request = new StringRequest(Request.Method.PUT, url,
                response -> {
                    Toast.makeText(this, "Đã cập nhật: " + newStatus, Toast.LENGTH_SHORT).show();
//                    loadAllOrders();
                },
                error -> Toast.makeText(this, "Lỗi cập nhật", Toast.LENGTH_SHORT).show()
        );
        Volley.newRequestQueue(this).add(request);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        android.widget.ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.UNSPECIFIED
            );
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}