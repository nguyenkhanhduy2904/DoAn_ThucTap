package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Voucher_admin extends AppCompatActivity {
    Button btn_them_voucher,btn_active,btn_end;
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
        addEvents();
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
    }
}