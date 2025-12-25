package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ttcn_dangnhap.adapter.OrderItemAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import models.OrderDTO;
import models.OrderItemDTO;

public class OrderDetail extends AppCompatActivity {

    OrderDTO orderDTO;
    List<OrderItemDTO> orderItemDTOList;
    ListView lvOrderItems;
    OrderItemAdapter orderItemAdapter;
    Button btnCancel, btnNextAction;
    ImageButton ibtnBack;

    TextView tvOrderId, tvOrderDate, tvOrderStatus, tvPaymentStatus, tvTotalAmount, tvAddress, tvReceiverName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        orderItemDTOList = new ArrayList<>();

        addControls();
        addEvents();
        getIntentData();
    }


    void addControls() {
        btnCancel = findViewById(R.id.btnCancel);
        btnNextAction = findViewById(R.id.btnNextAction);

        tvOrderId = findViewById(R.id.txtOrderId);
        tvOrderDate = findViewById(R.id.txtTime);
        tvOrderStatus = findViewById(R.id.txtOrderStatus);
        tvPaymentStatus = findViewById(R.id.txtPaymentStatus);
        tvTotalAmount = findViewById(R.id.txtTotalPrice);
        tvAddress = findViewById(R.id.txtAddress);
        tvReceiverName = findViewById(R.id.txtReceiverName);
        lvOrderItems = findViewById(R.id.lvOrderItems);
        ibtnBack = findViewById(R.id.ibtnBack);

        orderItemAdapter = new OrderItemAdapter(this, orderItemDTOList);
        lvOrderItems.setAdapter(orderItemAdapter);

        btnNextAction.setVisibility(View.GONE);
    }

    void addEvents() {
        btnCancel.setOnClickListener(view -> {
            // Handle cancel order action
            changeOrderStatus("Cancelled", orderDTO.getId(), orderDTO);
            Intent intent = new Intent(this, DonHang.class);
            startActivity(intent);
            finish();
        });
        ibtnBack.setOnClickListener(view -> {
            finish();
        });

    }
    void changeOrderStatus(String newStatus, int orderId, OrderDTO order) {
        String url = "http://10.0.2.2:8080/api/v1/orders/update-order-status/" + orderId
                + "?status=" + newStatus;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(
                Request.Method.PUT,
                url,
                response -> {
                    Toast.makeText(this, "Status updated: " + newStatus, Toast.LENGTH_SHORT).show();

                    // update the local order object
                    order.setTrangThaiDonHang(newStatus);

                    if(newStatus.equals("Finished") || newStatus.equals("Cancelled") || newStatus.equals("Refused")){
                        btnCancel.setVisibility(View.GONE);
                        btnNextAction.setVisibility(View.GONE);
                    }
                },
                error -> Toast.makeText(this, "Error updating status", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(request);
    }

    void getIntentData() {
        Intent intent = getIntent();
        List<OrderItemDTO> items = (List<OrderItemDTO>) intent.getSerializableExtra("orderItems");
        OrderDTO order = (OrderDTO) intent.getSerializableExtra("order");
        if (items != null && order != null) {
            orderDTO = order;
            tvOrderId.setText("Order ID: " + String.valueOf(order.getId()));

            Date orderTime = order.getThoiGianTao();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String orderTimeStr = sdf.format(orderTime);

            tvAddress.setText(order.getDiaChi());
            tvOrderDate.setText(orderTimeStr);
            tvOrderStatus.setText(order.getTrangThaiDonHang());
            tvPaymentStatus.setText(order.getTrangThaiThanhToan());
            tvTotalAmount.setText(order.getTongTien().toString() + " VND");
            tvReceiverName.setText(order.getTenNguoiNhan());

            orderItemDTOList.clear();
            orderItemDTOList.addAll(items);
            orderItemAdapter.notifyDataSetChanged();

            if(order.getTrangThaiDonHang().equals("Finished")||order.getTrangThaiDonHang().equals("Cancelled")||order.getTrangThaiDonHang().equals("Refused")){
                btnCancel.setVisibility(View.GONE);
            }
        }
    }
}