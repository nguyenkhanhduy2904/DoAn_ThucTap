package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ttcn_dangnhap.Adapter.CustomCartListAdapter;
import com.example.ttcn_dangnhap.Network.APIClient;
import com.example.ttcn_dangnhap.Network.VNPayAPI;

import java.util.List;

import models.Cart.CartItem;
import models.VNPayResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThanhToan extends AppCompatActivity {
    TextView tvTongTienThanhToan;
    ImageView imgBack;
    ListView lvDanhSachMonThanhToan;
    List<CartItem> listThanhToan;
    Button btnThanhtoan,btnSua;
    TextView tvUserName, tvUserPhone, tvAddress;
    RadioButton rbCOD, rbVNPay;
    CustomCartListAdapter adapter;

    SharedPreferences sharedPreferences;

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
        addEvents();
        setupDefaultData();
    }

    private void addEvents() {
        btnSua.setOnClickListener(view -> {showEdit();});
        imgBack.setOnClickListener(view -> {finish();});
        btnThanhtoan.setOnClickListener(view -> {
            if (!rbCOD.isChecked() && !rbVNPay.isChecked()) {
                Toast.makeText(this, "Vui lòng chọn phương thức thanh toán!", Toast.LENGTH_SHORT).show();
                return;
            }
            //long totalAmount = parseAmount(tvTongTienThanhToan.getText().toString());
            if (rbCOD.isChecked())
            {

            }
            if (rbVNPay.isChecked())
            {
                long sotien = Long.parseLong(tvTongTienThanhToan.getText().toString().replace("đ","").trim());
                VNPayAPI api = APIClient.getClient().create(VNPayAPI.class);
                api.createPayment(sotien).enqueue(new Callback<VNPayResponse>() {
                    @Override
                    public void onResponse(Call<VNPayResponse> call, Response<VNPayResponse> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(ThanhToan.this, VNPayWeb.class);
                            intent.putExtra("PAY_URL",response.body().getPaymentUrl());
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<VNPayResponse> call, Throwable t) {
                        Toast.makeText(ThanhToan.this,
                                "Không kết nối được server",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void addControls() {
        tvTongTienThanhToan = findViewById(R.id.tvTotalAmount);
        lvDanhSachMonThanhToan = findViewById(R.id.rvCartItems);
        btnSua=findViewById(R.id.btnSua);
        btnThanhtoan=findViewById(R.id.btnThanhtoan);
        tvUserName=findViewById(R.id.tvUserName);
        tvUserPhone=findViewById(R.id.tvUserPhone);
        tvAddress=findViewById(R.id.tvAddress);
        rbCOD=findViewById(R.id.rbCOD);
        rbVNPay=findViewById(R.id.rbVNPay);

        imgBack=findViewById(R.id.btnBack);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

    }

    void setupDefaultData(){
        tvUserName.setText(sharedPreferences.getString("username", ""));
        tvUserPhone.setText(sharedPreferences.getString("userPhone", ""));
        tvAddress.setText(sharedPreferences.getString("userAddress", ""));

    }
    void getDataFromIntent() {
        Intent intent = getIntent();

        String tongTien = intent.getStringExtra("TOTAL_PRICE");
        if(tongTien != null) {
            tvTongTienThanhToan.setText(tongTien + "đ");
        }


        listThanhToan = (List<CartItem>) intent.getSerializableExtra("CART_LIST");

        if (listThanhToan != null && listThanhToan.size() > 0) {
            adapter = new CustomCartListAdapter(this, listThanhToan, null);
            lvDanhSachMonThanhToan.setAdapter(adapter);
            setListViewHeight(lvDanhSachMonThanhToan);
        }
    }
    private void showEdit()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_sua_tt_nhan, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        EditText edtName = dialogView.findViewById(R.id.edtName);
        EditText edtPhone = dialogView.findViewById(R.id.edtPhone);
        EditText edtAddress = dialogView.findViewById(R.id.edtAddress);
        AppCompatButton btnConfirm = dialogView.findViewById(R.id.btnConfirm);
        edtName.setText(tvUserName.getText().toString());
        edtPhone.setText(tvUserPhone.getText().toString());
        edtAddress.setText(tvAddress.getText().toString());
        btnConfirm.setOnClickListener(view -> {
            String newName = edtName.getText().toString().trim();
            String newPhone = edtPhone.getText().toString().trim();
            String newAddress = edtAddress.getText().toString().trim();
            if (newName.isEmpty() || newPhone.isEmpty() || newAddress.isEmpty()) {
                Toast.makeText(ThanhToan.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            tvUserName.setText(newName);
            tvUserPhone.setText(newPhone);
            tvAddress.setText(newAddress);
            dialog.dismiss();

            Toast.makeText(ThanhToan.this, "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show();
        });
        dialog.show();
    }
    void setListViewHeight(ListView lsView){
        ListAdapter adapter = lsView.getAdapter();
        if(adapter == null) return;
        int totalHeight = 0;
        for(int i = 0 ; i< adapter.getCount(); i++){

            View listItem = adapter.getView(i, null, lsView);
            listItem.measure(0,0);
            totalHeight+=listItem.getMeasuredHeight();

        }
        ViewGroup.LayoutParams params = lsView.getLayoutParams();
        params.height = totalHeight + (lsView.getDividerHeight() * (adapter.getCount()-1));
        lsView.setLayoutParams(params);
        lsView.requestLayout();
    }
}
