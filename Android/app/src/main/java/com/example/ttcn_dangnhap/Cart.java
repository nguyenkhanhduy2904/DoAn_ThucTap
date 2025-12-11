package com.example.ttcn_dangnhap;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ttcn_dangnhap.Network.APIClient;
import com.example.ttcn_dangnhap.Network.APIService;
import com.example.ttcn_dangnhap.adapter.CartAdapter;
import com.example.ttcn_dangnhap.util.CartUtil;
import com.google.android.material.button.MaterialButton;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.APIResponse; // Import class model của bạn
import models.CartItem;
import models.OrderDTO;
import models.OrderItemDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerViewCart;
    TextView tvPrice, tvDiscountValue;
    MaterialButton btnConfirm;
    ImageView btnBack;
    CartAdapter adapter;
    long tongTienHang = 0;

    // GIẢ LẬP NGƯỜI DÙNG ĐANG ĐĂNG NHẬP (Lấy từ SharedPreference hoặc Global variable)
    // ID = 2 tương ứng với user 'kduy' trong database bạn gửi
    private final int CURRENT_USER_ID = 1;
    private final String CURRENT_USER_NAME = "Nguyen Khanh Duy";
    private final String CURRENT_USER_ADDRESS = "TP.HCM";
    private final String CURRENT_USER_PHONE = "0827281099";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
        setupRecyclerView();
        updateTotalPrice();
        addEvents();
    }

    private void initView() {
        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        tvPrice = findViewById(R.id.tvPrice);
        tvDiscountValue = findViewById(R.id.tvDiscountValue);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupRecyclerView() {
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(this, CartUtil.mangGioHang, new CartAdapter.OnCartChangeListener() {
            @Override
            public void onCartChanged() {
                updateTotalPrice();
            }
        });
        recyclerViewCart.setAdapter(adapter);
    }

    private void updateTotalPrice() {
        tongTienHang = 0;
        for (CartItem item : CartUtil.mangGioHang) {
            tongTienHang += (item.getMonAn().getGiaMonAn() * item.getQuantity());
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        tvPrice.setText(formatter.format(tongTienHang));
        tvDiscountValue.setText("0"); // Demo giảm giá = 0

        if (CartUtil.mangGioHang.isEmpty()) {
            tvPrice.setText("0");
        }
    }

    private void addEvents() {
        btnBack.setOnClickListener(v -> finish());

        btnConfirm.setOnClickListener(v -> {
            if (CartUtil.mangGioHang.isEmpty()) {
                Toast.makeText(Cart.this, "Giỏ hàng đang trống!", Toast.LENGTH_SHORT).show();
            } else {
                // GỌI HÀM THANH TOÁN
                sendOrderToServer();
            }
        });
    }

    // --- HÀM GỬI DỮ LIỆU LÊN SERVER ---
    private void sendOrderToServer() {
        Toast.makeText(this, "Đang xử lý đặt hàng...", Toast.LENGTH_SHORT).show();

        // 1. Chuyển đổi danh sách CartItem (Android) thành OrderItemDTO (API)
        List<OrderItemDTO> orderItems = new ArrayList<>();

        for (CartItem cartItem : CartUtil.mangGioHang) {
            BigDecimal giaDon = new BigDecimal(cartItem.getMonAn().getGiaMonAn());
            BigDecimal giaTong = giaDon.multiply(new BigDecimal(cartItem.getQuantity()));

            // Tạo item DTO
            OrderItemDTO itemDTO = new OrderItemDTO(
                    cartItem.getMonAn().getTenMonAn(),
                    cartItem.getQuantity(),
                    cartItem.getNote(), // Ghi chú
                    giaDon,
                    giaTong,
                    cartItem.getMonAn().getIdMonAn()
            );
            orderItems.add(itemDTO);
        }

        // 2. Tạo đối tượng OrderDTO chứa danh sách items
        OrderDTO orderDTO = new OrderDTO(
                CURRENT_USER_NAME,      // Tên khách
                CURRENT_USER_ADDRESS,   // Địa chỉ
                CURRENT_USER_PHONE,     // SĐT
                new BigDecimal(tongTienHang), // Tổng tiền
                CURRENT_USER_ID,        // ID User (quan trọng để map khóa ngoại)
                orderItems              // Danh sách món ăn
        );

        // 3. Gọi Retrofit
        APIService apiService = APIClient.getClient().create(APIService.class);
        apiService.addOrder(orderDTO).enqueue(new Callback<APIResponse<OrderDTO>>() {
            @Override
            public void onResponse(Call<APIResponse<OrderDTO>> call, Response<APIResponse<OrderDTO>> response) {
                // Kiểm tra response từ server
                if (response.isSuccessful() && response.body() != null) {
                    APIResponse<OrderDTO> apiResponse = response.body();

                    if (apiResponse.getCode() == 200) {
                        // Thành công
                        Toast.makeText(Cart.this, "Đặt hàng thành công! Mã đơn: " + apiResponse.getData().getId(), Toast.LENGTH_LONG).show();

                        // Xóa giỏ hàng và cập nhật UI
                        CartUtil.mangGioHang.clear();
                        adapter.notifyDataSetChanged();
                        updateTotalPrice();

                        // Đóng màn hình hoặc chuyển hướng
                        finish();
                    } else {
                        // Backend trả về lỗi logic (ví dụ: thiếu tiền, lỗi user...)
                        Toast.makeText(Cart.this, "Lỗi: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Lỗi HTTP (404, 500...)
                    Toast.makeText(Cart.this, "Lỗi server: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<APIResponse<OrderDTO>> call, Throwable t) {
                // Lỗi mạng hoặc lỗi parse JSON
                Toast.makeText(Cart.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("CartError", "Retrofit error: " + t.getMessage());
            }
        });
    }
}