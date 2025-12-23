package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ttcn_dangnhap.adapter.OrderItemAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import models.OrderDTO;
import models.OrderItemDTO;

public class OrderDetail extends AppCompatActivity {

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
        });
//        btnNextAction.setOnClickListener(view -> {
//            // Handle next action based on order status
//        });
        ibtnBack.setOnClickListener(view -> {
            finish();
        });

    }

    void getIntentData() {
        Intent intent = getIntent();
        List<OrderItemDTO> items = (List<OrderItemDTO>) intent.getSerializableExtra("orderItems");
        OrderDTO order = (OrderDTO) intent.getSerializableExtra("order");
        if (items != null && order != null) {
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