package com.example.ttcn_dangnhap;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.IDNA;
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
    LinearLayout layoutGuest, layoutUserTop, layoutUserBottom,ibtnHome;
    MaterialCardView btnLogout,btnUserInfo;
    TextView btnGoToLogin, btnGoToRegister;
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
        ibtnHome = findViewById(R.id.ibtnHome);
        btnGoToLogin = findViewById(R.id.btnGoToLogin);
        btnGoToRegister = findViewById(R.id.btnGoToRegister);
        btnLogout = findViewById(R.id.btnLogout);
        btnUserInfo = findViewById(R.id.btnUserInfo);
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
            String newName = edtName.getText().toString().trim();
            String newPhone = edtPhone.getText().toString().trim();
            String newAddress = edtAddress.getText().toString().trim();

            boolean isNameChanged = !newName.equals(userDTO.getTenHienThi() != null ? userDTO.getTenHienThi() : "");
            boolean isPhoneChanged = !newPhone.equals(userDTO.getSdt() != null ? userDTO.getSdt() : "");
            boolean isAddressChanged = !newAddress.equals(userDTO.getDiaChi() != null ? userDTO.getDiaChi() : "");

            if (!isNameChanged && !isPhoneChanged && !isAddressChanged) {
                dialog.dismiss();
            }
            else {
                if (newName.isEmpty() || newPhone.isEmpty()) {
                    Toast.makeText(Infor.this, "Tên và SĐT không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserDTO updateRequest = new UserDTO(newName, newPhone, newAddress);
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                int userId = settings.getInt("userid", -1);

                APIService apiService = APIClient.getClient().create(APIService.class);
                apiService.updateUser(userId, updateRequest).enqueue(new Callback<APIResponse<Void>>() {
                    @Override
                    public void onResponse(Call<APIResponse<Void>> call, Response<APIResponse<Void>> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(Infor.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString(KEY_USERNAME, newName);
                            editor.putString(KEY_PHONE, newPhone);
                            editor.putString(KEY_ADDRESS, newAddress);
                            editor.apply();

                            apiService.getUserDetail(userId).enqueue(new Callback<APIResponse<UserDTO>>() {
                                @Override
                                public void onResponse(Call<APIResponse<UserDTO>> call, Response<APIResponse<UserDTO>> response) {
                                    if(response.isSuccessful() && response.body() != null){
                                        currentUserData = response.body().getData();
                                        edtName.setText(currentUserData.getTenHienThi());
                                        edtPhone.setText(currentUserData.getSdt());
                                        edtAddress.setText(currentUserData.getDiaChi());
                                    }
                                }
                                @Override
                                public void onFailure(Call<APIResponse<UserDTO>> call, Throwable t) {}
                            });

                        } else {
                            Toast.makeText(Infor.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<APIResponse<Void>> call, Throwable t) {
                        Toast.makeText(Infor.this, "Lỗi mạng!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.show();
    }
}