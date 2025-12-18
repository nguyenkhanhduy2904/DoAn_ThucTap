package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.PixelCopy;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ttcn_dangnhap.Adapter.CustomCartListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import models.Cart.CartItem;

public class ThanhToan extends AppCompatActivity {
    TextView tvTongTienThanhToan;
    ImageView imgBack;
    ListView lvDanhSachMonThanhToan;
    List<CartItem> listThanhToan;
    Button btnThanhtoan,btnSua;
    TextView tvUserName, tvUserPhone, tvAddress;
    RadioButton rbCOD, rbVNPay;
    CustomCartListAdapter adapter;

    String TrangThaiDonHang = "Pending";
    String TrangThaiThanhToan = "Pending";

    String PaymentMethod = "";

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
                PaymentMethod = "COD";
            }
            else if (rbVNPay.isChecked())
            {
                PaymentMethod = "VNPay";
            }

            buildMessage();
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

    void buildMessage(){

        JSONArray cartList = new JSONArray();

        for(int i = 0; i < listThanhToan.size(); i++){
            try{
                    CartItem item = listThanhToan.get(i);
                    JSONObject jsonItem = new JSONObject();
                    jsonItem.put("tenMon",item.getTenMon() );
                    jsonItem.put("soLuong", item.getSoLuong());
                    jsonItem.put("ghiChu", item.getGhiChu());
                    jsonItem.put("giaTungMon", item.getGiaTungMon());
                    jsonItem.put("giaTongMon", item.getGiaTongMon());
                    jsonItem.put("monanid", item.getMonanid());
                    cartList.put(jsonItem);
                }
            catch (Exception e){
                    Toast.makeText(ThanhToan.this, e.getMessage(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
            }
        }

        JSONObject orderMessage = new JSONObject();
        try{
            JSONObject orderInfo = new JSONObject();
            orderInfo.put("tenKhachHang",tvUserName.getText().toString().trim());
            orderInfo.put("diaChi", tvAddress.getText().toString().trim());
            orderInfo.put("sdt", tvUserPhone.getText().toString().trim());
            orderInfo.put("phuongThucThanhToan", PaymentMethod);
            orderInfo.put("trangThaiDonHang", TrangThaiDonHang);
            orderInfo.put("trangThaiThanhToan", TrangThaiThanhToan);
            orderInfo.put("thoiGianTao", System.currentTimeMillis());
            orderInfo.put("tongTien", tvTongTienThanhToan.getText().toString().trim());
            orderInfo.put("idKhachHang", sharedPreferences.getInt("userid",-1));
            orderInfo.put("items", cartList);
        }
        catch (Exception e){
            Toast.makeText(ThanhToan.this,"error build order info"+ e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        String url = "http://10.0.2.2:8080/api/v1/orders";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                orderMessage,
                reponse-> {
                    try{
                        String status = reponse.getString("status");
                        String message = reponse.getString("message");

                        if (status.equals("success")) {
                            Toast.makeText(ThanhToan.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ThanhToan.this, "Đặt hàng thất bại: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(ThanhToan.this, "eroor2:"+e.getMessage(),Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        try {
                            String errorJson = new String(error.networkResponse.data, "UTF-8");
                            JSONObject obj = new JSONObject(errorJson);

//                            String status = obj.getString("status");
                            String message = obj.getString("message");

                            Toast.makeText(this, "eorrro3 "+message, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, "Error parsing error response", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "Connection Error", Toast.LENGTH_LONG).show();
                    }
                }
        );

        queue.add(request);


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
