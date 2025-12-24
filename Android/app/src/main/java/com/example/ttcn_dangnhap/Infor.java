package com.example.ttcn_dangnhap;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ttcn_dangnhap.Network.APIClient;
import com.example.ttcn_dangnhap.Network.APIService;
import com.google.android.material.card.MaterialCardView;

import models.APIResponse;
import models.UserDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Infor extends AppCompatActivity {
    LinearLayout layoutGuest, layoutUserTop, layoutUserBottom;

    MaterialCardView btnLogout,btnUserInfo;
    TextView btnGoToLogin, btnGoToRegister;
    ConstraintLayout navBarAdmin;
    ConstraintLayout navBarCustomer;


    LinearLayout ibtnFoodManagement, ibtnOrderAdmin, ibtnAccountManagement, ibtnYourAccountAdmin;
    LinearLayout ibtnHome, ibtnOrderCustomer;




    private boolean isLoggedIn = false;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PHONE = "userPhone";
    private static final String KEY_ADDRESS = "userAddress";
    private UserDTO currentUserData = null;
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

        btnGoToLogin = findViewById(R.id.btnGoToLogin);
        btnGoToRegister = findViewById(R.id.btnGoToRegister);
        btnLogout = findViewById(R.id.btnLogout);
        btnUserInfo = findViewById(R.id.btnUserInfo);

        //find the nav bar
        navBarAdmin = findViewById(R.id.navBarAdmin);
        navBarCustomer = findViewById(R.id.navBarCustomer);


        //set control for admin
        ibtnYourAccountAdmin = findViewById(R.id.ibtnMyAccountAdmin);
        ibtnOrderAdmin = findViewById(R.id.ibtnOrderAdmin);
        ibtnAccountManagement = findViewById(R.id.ibtnAccountManagement);
        ibtnFoodManagement = findViewById(R.id.ibtnFoodManagementAdmin);

        //set control for customer
        ibtnHome = findViewById(R.id.ibtnHome);
        ibtnOrderCustomer = findViewById(R.id.ibtnOrderCustomer);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String role = sharedPreferences.getString("user_role", "CUSTOMER"); // default CUSTOMER

        if(role.equals("ADMIN")) {
            navBarAdmin.setVisibility(View.VISIBLE);
            navBarCustomer.setVisibility(View.GONE);

            ibtnYourAccountAdmin.setOnClickListener(view -> {
                Intent intent = new Intent(this, Infor.class);
                startActivity(intent);
            });

            ibtnOrderAdmin.setOnClickListener(view -> {
                Intent intent = new Intent(this, AdminOrder.class);
                startActivity(intent);
            });
            ibtnFoodManagement.setOnClickListener(view -> {
                Intent intent = new Intent(this, QuanLyMon.class);
                startActivity(intent);
            });


        } else {
            navBarAdmin.setVisibility(View.GONE);
            navBarCustomer.setVisibility(View.VISIBLE);
        }



    }

    private void addEvents() {

        //set this for admin
        ibtnYourAccountAdmin.setOnClickListener(view -> {
            Intent intent = new Intent(this, Infor.class);
            startActivity(intent);
        });

        ibtnOrderAdmin.setOnClickListener(view -> {
            Intent intent = new Intent(this, AdminOrder.class);
            startActivity(intent);
        });
        ibtnFoodManagement.setOnClickListener(view -> {
            Intent intent = new Intent(this, QuanLyMon.class);
            startActivity(intent);
        });
        ibtnAccountManagement.setOnClickListener(view -> {
            Intent intent = new Intent(this, AccountManagement.class);
            startActivity(intent);
            finish();
        });

        //set this for customer
        ibtnHome.setOnClickListener(view -> {
            Intent intent = new Intent(Infor.this,HomePage.class);
            startActivity(intent);
        });
        ibtnOrderCustomer.setOnClickListener(view -> {
            Intent intent = new Intent(Infor.this,DonHang.class);
            startActivity(intent);
        });



        btnGoToLogin.setOnClickListener(view -> {
            Intent intent = new Intent(Infor.this,Login.class);
            startActivity(intent);

        });
        btnGoToRegister.setOnClickListener(view -> {
            Intent intent = new Intent(Infor.this,Dangky.class);
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
        btnUserInfo.setOnClickListener(view -> {
            fetchUserAndShowDialog();
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
    private void fetchUserAndShowDialog() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int userId = settings.getInt("userid", -1);

        if (userId == -1) {
            Toast.makeText(this, "Không tìm thấy ID user", Toast.LENGTH_SHORT).show();
            return;
        }

        APIService apiService = APIClient.getClient().create(APIService.class);
        apiService.getUserDetail(userId).enqueue(new Callback<APIResponse<UserDTO>>() {
            @Override
            public void onResponse(Call<APIResponse<UserDTO>> call, Response<APIResponse<UserDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    currentUserData = response.body().getData();
                    showEditInfoDialog(currentUserData);
                } else {
                    Toast.makeText(Infor.this, "Lỗi lấy thông tin: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<APIResponse<UserDTO>> call, Throwable t) {
                Toast.makeText(Infor.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showEditInfoDialog(UserDTO userDTO) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Infor.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_sua_tt_nhan, null);
        builder.setView(dialogView);

        EditText edtName = dialogView.findViewById(R.id.edtName);
        EditText edtPhone = dialogView.findViewById(R.id.edtPhone);
        EditText edtAddress = dialogView.findViewById(R.id.edtAddress);
        AppCompatButton btnConfirm = dialogView.findViewById(R.id.btnConfirm);

        edtName.setText(userDTO.getTenHienThi() != null ? userDTO.getTenHienThi() : "");
        edtPhone.setText(userDTO.getSdt() != null ? userDTO.getSdt() : "");
        edtAddress.setText(userDTO.getDiaChi() != null ? userDTO.getDiaChi() : "");

        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        btnConfirm.setOnClickListener(v -> {
            //can cap nhat lai user pref
        });

        dialog.show();
    }
}