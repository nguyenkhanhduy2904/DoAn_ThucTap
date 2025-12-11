package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuanLyMon extends AppCompatActivity {
    Button btn_them_mon;
    LinearLayout layoutVietNam, layoutThaiLand, layoutSKorea, layoutChina, layoutBestSell;
    ListView lv_danh_sach_mon_an;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_mon);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();

    }

    private void addControls() {
        btn_them_mon = findViewById(R.id.btn_them_mon);
        layoutBestSell = findViewById(R.id.layoutBestSell);
        layoutVietNam = findViewById(R.id.layoutVietNam);
        layoutThaiLand = findViewById(R.id.layoutThaiLand);
        layoutSKorea = findViewById(R.id.layoutSKorea);
        layoutChina = findViewById(R.id.layoutChina);
    }

    private void addEvents() {
        btn_them_mon.setOnClickListener(view -> {
            Intent intent = new Intent(QuanLyMon.this,ThemMon.class);
        });
    }
}