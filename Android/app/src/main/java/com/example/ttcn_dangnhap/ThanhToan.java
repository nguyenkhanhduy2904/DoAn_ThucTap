package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ttcn_dangnhap.adapter.CustomCartListAdapter;

import java.util.List;

import models.Cart.CartItem;

public class ThanhToan extends AppCompatActivity {
    TextView tvTongTienThanhToan;
    ListView lvDanhSachMonThanhToan;
    List<CartItem> listThanhToan;
    com.example.ttcn_dangnhap.adapter.CustomCartListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thanh_toan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        getDataFromIntent();
    }

    private void addControls() {
        tvTongTienThanhToan = findViewById(R.id.tvTotalAmount); // Ví dụ ID
        lvDanhSachMonThanhToan = findViewById(R.id.rvCartItems);
    }
    void getDataFromIntent() {
        Intent intent = getIntent();

        // 1. Nhận tổng tiền
        String tongTien = intent.getStringExtra("TOTAL_PRICE");
        if(tongTien != null) {
            tvTongTienThanhToan.setText(tongTien + "đ");
        }

        // 2. Nhận danh sách món ăn
        // Cần ép kiểu về List<CartItem>
        listThanhToan = (List<CartItem>) intent.getSerializableExtra("CART_LIST");

        if (listThanhToan != null && listThanhToan.size() > 0) {
            adapter = new CustomCartListAdapter(this, listThanhToan, null);
            lvDanhSachMonThanhToan.setAdapter(adapter);
        }
    }
}
