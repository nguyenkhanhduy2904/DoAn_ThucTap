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
        btnAddToCart.setOnClickListener(view -> {
            Intent intent = new Intent(Chi_tiet_mon.this, Cart.class);
            startActivity(intent);
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

        if (intent != null && intent.hasExtra("monAn")) {
            currentFood = (MonAn) intent.getSerializableExtra("monAn");

            tvFoodName.setText(currentFood.getTenMonAn());
            tvDescription.setText(currentFood.getMotaMonAn());

            DecimalFormat formatter = new DecimalFormat("###,###,###");
            tvPrice.setText(formatter.format(currentFood.getGiaMonAn()));
            Picasso.get().load(currentFood.getUrlHinhAnhMonAn()).resize(500,300).centerCrop().into(imgFood);



        }}




}