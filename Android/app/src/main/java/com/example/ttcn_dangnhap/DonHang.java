package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
import com.example.ttcn_dangnhap.adapter.OrderAdapter2;

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

public class DonHang extends AppCompatActivity {
    TextView tabTracking, tabHistory;
//    RecyclerView rvOrders, rvHistory;


    ListView lvOrders;
    OrderAdapter2 orderAdapter2;

    LinearLayout ibtnHome, ibtnVoucher, ibtnOrder, ibtnAccount;


    List<OrderDTO> trackingList = new ArrayList<>();
    List<OrderDTO> historyList = new ArrayList<>();

    List<OrderDTO> currentViewList = new ArrayList<>();
    String currentTab = "TRACKING"; // or HISTORY


    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable autoReloadRunnable;
    private static final long REFRESH_INTERVAL = 5000;



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

        autoReloadRunnable = new Runnable() {
            @Override
            public void run() {
                loadThisUserOrders(new APICallback<List<OrderDTO>>() {
                    @Override
                    public void onSuccess(List<OrderDTO> result) {

                        trackingList.clear();
                        historyList.clear();

                        for (OrderDTO order : result) {
                            String status = order.getTrangThaiDonHang();

                            if (status.equals("Finished")
                                    || status.equals("Cancelled")
                                    || status.equals("Refused")) {
                                historyList.add(order);
                            } else {
                                trackingList.add(order);
                            }
                        }

                        // Update what user is currently seeing
                        currentViewList.clear();
                        if (currentTab.equals("TRACKING")) {
                            currentViewList.addAll(trackingList);
                        } else {
                            currentViewList.addAll(historyList);
                        }

                        orderAdapter2.notifyDataSetChanged();
                        Toast.makeText(DonHang.this, "timer running", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e("DonHang", errorMessage);
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


    private void addEvents() {
        tabTracking.setOnClickListener(v -> {

            currentTab = "TRACKING";

            tabTracking.setBackgroundColor(0xFF69E0D4);
            tabHistory.setBackgroundColor(0xFFB2DFDB);

            currentViewList.clear();
            currentViewList.addAll(trackingList);
            orderAdapter2.notifyDataSetChanged();

        });

        tabHistory.setOnClickListener(v -> {
            currentTab = "HISTORY";

            tabTracking.setBackgroundColor(0xFFB2DFDB);
            tabHistory.setBackgroundColor(0xFF69E0D4);

            currentViewList.clear();
            currentViewList.addAll(historyList);
            orderAdapter2.notifyDataSetChanged();
        });
        ibtnHome.setOnClickListener(view -> {
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
            finish();
            overridePendingTransition(0,0);
            Toast.makeText(this, "Clicked Home", Toast.LENGTH_SHORT).show();
        });
        ibtnOrder.setOnClickListener(view -> {

//            Toast.makeText(HomePage.this, "Clicked Order", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,DonHang.class);
            startActivity(intent);
            finish();


        });
        ibtnAccount.setOnClickListener(view -> {
            Intent intent = new Intent(this,Infor.class);
            startActivity(intent);
            finish();
        });

    }

    private void addControls() {
        tabTracking = findViewById(R.id.tabTracking);
        tabHistory = findViewById(R.id.tabHistory);
        lvOrders = findViewById(R.id.lvOrders);
        // After you have loaded the trackingList
        orderAdapter2 = new OrderAdapter2(this, currentViewList, lvOrders);
        lvOrders.setAdapter(orderAdapter2);

        orderAdapter2.setStatusChangeCallback(new APICallback<OrderDTO>() {
            @Override
            public void onSuccess(OrderDTO result) {
                trackingList.remove(result);
                historyList.add(result);

                currentViewList.remove(result);
                orderAdapter2.notifyDataSetChanged();

                Toast.makeText(DonHang.this, "Đã hủy đơn hàng", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(DonHang.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        ibtnHome = findViewById(R.id.ibtnHome);

        ibtnOrder = findViewById(R.id.ibtnOrderCustomer);
        ibtnAccount = findViewById(R.id.ibtnAccount);



        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
    }


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
                            orderList.sort((o1,o2)-> o2.getThoiGianTao().compareTo(o1.getThoiGianTao()));
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


    public static void setListViewHeight(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) return;

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}