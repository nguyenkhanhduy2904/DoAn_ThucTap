package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class Infor extends AppCompatActivity {
    LinearLayout layoutGuest, layoutUserTop, layoutUserBottom,ibtnHome;
    MaterialCardView btnLogout;
    TextView btnGoToLogin, btnGoToRegister;
    private boolean isLoggedIn = false;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_infor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLoginState();
    }

    private void addControls() {
        layoutGuest = findViewById(R.id.layoutGuest);
        layoutUserBottom = findViewById(R.id.layoutUserBottom);
        layoutUserTop = findViewById(R.id.layoutUserTop);
        ibtnHome = findViewById(R.id.ibtnHome);
        btnGoToLogin = findViewById(R.id.btnGoToLogin);
        btnGoToRegister = findViewById(R.id.btnGoToRegister);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void addEvents() {
        btnGoToLogin.setOnClickListener(view -> {
            Intent intent = new Intent(Infor.this,Login.class);
            startActivity(intent);

        });
        btnGoToRegister.setOnClickListener(view -> {
            Intent intent = new Intent(Infor.this,Dangky.class);
            startActivity(intent);
        });
        ibtnHome.setOnClickListener(view -> {
            Intent intent = new Intent(Infor.this,HomePage.class);
            startActivity(intent);
        });
        btnLogout.setOnClickListener(view -> {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(KEY_IS_LOGGED_IN, false);
            editor.remove("userid");
            editor.remove("user_role");
            editor.remove("userAddress");
            editor.remove("userPhone");
            editor.remove("username");
            editor.apply();

            Intent intent = new Intent(Infor.this, Login.class);
            startActivity(intent);
            finish();

            Toast.makeText(Infor.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
            checkLoginState();




        });
    }
    private void checkLoginState() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isLoggedIn = settings.getBoolean(KEY_IS_LOGGED_IN, false);
        if (isLoggedIn) {
            layoutGuest.setVisibility(View.GONE);
            layoutUserTop.setVisibility(View.VISIBLE);
            layoutUserBottom.setVisibility(View.VISIBLE);
        } else {
            layoutGuest.setVisibility(View.VISIBLE);
            layoutUserTop.setVisibility(View.GONE);
            layoutUserBottom.setVisibility(View.GONE);
        }
    }
}