package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import models.MonAn;

public class Chi_tiet_mon extends AppCompatActivity {
    Button btnAddToCart,btngiam,btnthem;
    TextView tvSl,tvFoodName,tvDescription,tvPrice;
    ImageView imgFood;
    ImageButton btnBack,btnCart;
    int quantity = 1;
    long tongtien = 0;
    MonAn currentFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet_mon);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        getIntentData();
        if  (currentFood != null) {
            updatePriceUI();
        }
        addEvents();
    }

    private void addControls() {
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btngiam = findViewById(R.id.btngiam);
        btnthem = findViewById(R.id.btnthem);
        tvSl = findViewById(R.id.tvSl);
        tvFoodName = findViewById(R.id.tvFoodName);
        tvDescription = findViewById(R.id.tvDescription);
        tvPrice = findViewById(R.id.tvPrice);
        imgFood=findViewById(R.id.imgFood);
        btnBack=findViewById(R.id.btnBack);
        btnCart=findViewById(R.id.btnCart);
    }

    private void addEvents() {
        btnBack.setOnClickListener(view -> finish());
        btnthem.setOnClickListener(view -> {
            quantity++;
            updatePriceUI();
        });
        btngiam.setOnClickListener(view -> {
            if (quantity>1)
            {
                quantity--;
                updatePriceUI();
            }
        });
    }
    private void updatePriceUI() {
        tvSl.setText(String.valueOf(quantity));
        tongtien = currentFood.getGiaMonAn() * quantity;
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String formattedPrice = formatter.format(tongtien);
        tvPrice.setText(formattedPrice);
    }
    private void getIntentData() {
        Intent intent = getIntent();
        // Kiểm tra xem có dữ liệu gửi sang không
        if (intent != null && intent.hasExtra("monAn")) {
            // Nhận đối tượng MonAn (ép kiểu về MonAn)
            currentFood = (MonAn) intent.getSerializableExtra("monAn");

            // --- HIỂN THỊ DỮ LIỆU LÊN GIAO DIỆN ---

            // 1. Tên và Mô tả
            tvFoodName.setText(currentFood.getTenMonAn());
            tvDescription.setText(currentFood.getMotaMonAn());

            // 2. Giá tiền (Định dạng cho đẹp)
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            tvPrice.setText(formatter.format(currentFood.getGiaMonAn()));
            Picasso.get().load(currentFood.getUrlHinhAnhMonAn()).resize(500,300).centerCrop().into(imgFood);



        }}



//            // 3. Hiển thị ảnh từ URL (Dùng Glide)

//            Glide.with(this)
//                    .load(currentFood.getUrlHinhAnhMonAn()) // Link ảnh https://...
//                    .placeholder(R.drawable.logo) // Ảnh chờ khi đang tải (tạo 1 ảnh tạm)
//                    .error(R.drawable.logo) // Ảnh lỗi nếu link chết
//                    .into(imgFood);
//        }
}