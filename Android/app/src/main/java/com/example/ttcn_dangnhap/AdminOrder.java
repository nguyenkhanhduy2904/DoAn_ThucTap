package com.example.ttcn_dangnhap;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminOrder extends AppCompatActivity {
    AppCompatButton btnCho, btnXacNhan, btnSanSang, btnDaGiao;

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
        addEvents();
    }

    private void addControls() {
        btnCho=findViewById(R.id.btnCho);
        btnSanSang=findViewById(R.id.btnSanSang);
        btnXacNhan=findViewById(R.id.btnXacNhan);
        btnDaGiao=findViewById(R.id.btnDaGiao);
    }

    private void addEvents() {

    }
}