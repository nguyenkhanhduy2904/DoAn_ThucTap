package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import models.Voucher;

public class Voucher_admin extends AppCompatActivity {
    Button btn_them_voucher,btn_active,btn_end;
    ListView lvDanhSach;
    public static ArrayList<Voucher> dsvc = new ArrayList<>();
    VoucherAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voucher_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        setupListView();
        getData();
        addEvents();
    }

    private void setupListView() {
        adapter = new VoucherAdapter(this, R.layout.item_voucher, dsvc);
        lvDanhSach.setAdapter(adapter);
    }

    private void addEvents() {
        btn_them_voucher.setOnClickListener(view -> {
            Intent intent = new Intent(Voucher_admin.this,Them_Voucher.class);
            startActivity(intent);
        });
    }

    private void addControls() {
        btn_them_voucher=findViewById(R.id.btn_them_voucher);
        btn_active=findViewById(R.id.btn_active);
        btn_end=findViewById(R.id.btn_end);
        lvDanhSach = findViewById(R.id.lv_danh_sach_voucher);
    }
    private void getData(){
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TEN")) {
            String ten = intent.getStringExtra("TEN");
            String moTa = intent.getStringExtra("MO_TA");
            String ngayBD = intent.getStringExtra("NGAY_BD");
            String ngayKT = intent.getStringExtra("NGAY_KT");
            String loai = intent.getStringExtra("LOAI");
            String dieuKien = intent.getStringExtra("DIEU_KIEN");
            String anhUriString = intent.getStringExtra("ANH");
            Uri anh = null;
            if (anhUriString != null) {
                anh = Uri.parse(anhUriString);
            }
            Voucher newVoucher = new Voucher(ten, moTa, loai, ngayBD,ngayKT, anhUriString);
            dsvc.add(0, newVoucher);
            adapter.notifyDataSetChanged();
        }
    }
}