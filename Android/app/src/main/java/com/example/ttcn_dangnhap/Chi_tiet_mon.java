package com.example.ttcn_dangnhap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ttcn_dangnhap.util.CartUtil;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import models.Cart.CartDAO;
import models.Cart.CartDbHelper;
import models.Cart.CartItem;
import models.MonAn;

public class Chi_tiet_mon extends AppCompatActivity {
    Button btnAddToCart,btngiam,btnthem;
    TextView tvSl,tvFoodName,tvDescription,tvPrice;
    ImageView imgFood;
    ImageButton btnBack,btnCart;
    int quantity = 1;
    long tongtien = 0;
    MonAn currentFood;
    EditText etNote;
    int userid;

    CartDbHelper cartDbHelper;
    CartDAO cartDAO;
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
        SharedPreferences sp = this.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userid = sp.getInt("userid", -1);


        cartDbHelper = new CartDbHelper(this);
        cartDAO = new CartDAO(cartDbHelper);


        addControls();
        getIntentData();
        if  (currentFood != null) {
            updatePriceUI();
        }
        addEvents();
//        if(currentFood==null){
//            Toast.makeText(this, "null current food", Toast.LENGTH_LONG).show();
//        }
//        else {
//            Toast.makeText(this, currentFood.toString(), Toast.LENGTH_LONG).show();
//        }


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
        etNote = findViewById(R.id.etNote);
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
        btnCart.setOnClickListener(view -> {
            Intent intent = new Intent(Chi_tiet_mon.this, Cart.class);
            startActivity(intent);
        });
        btnAddToCart.setOnClickListener(view -> {
            if (currentFood != null) {
//                // Kiểm tra món này đã có trong giỏ chưa
//                String noteContent = etNote.getText().toString().trim();
//                boolean exists = false;
//                for (CartItem item : CartUtil.mangGioHang) {
//                    if (item.getMonAn().getIdMonAn() == currentFood.getIdMonAn()) {
//                        // Nếu đã có, cộng dồn số lượng
//                        item.setQuantity(item.getQuantity() + quantity);
//                        if(!noteContent.isEmpty()){
//                            item.setNote(noteContent);
//                        }
//                        exists = true;
//                        break;
//                    }
//                }
//
//                // Nếu chưa có, thêm mới (Ghi chú đang để rỗng, bạn có thể thêm EditText nhập ghi chú ở màn hình này nếu muốn)
//                if (!exists) {
//                    CartUtil.mangGioHang.add(new CartItem(currentFood, quantity, noteContent));
//                }

                int foodid = currentFood.getIdMonAn();
                if (userid == -1) {
                    Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
                    return;
                }


                String thisOrderGhiChu = etNote.getText().toString().trim();

                List<CartItem> cartItems = cartDAO.getAllItemWithThisUserAndMonAnId(userid, foodid);

                CartItem noNoteCartTtem = null;
                for(int i =0; i< cartItems.size();i++){
                    CartItem item = cartItems.get(i);
                    if(item.getGhiChu()==null || item.getGhiChu().isBlank()){
                        noNoteCartTtem = item;
                        break;
                    }
                }

                if((thisOrderGhiChu == null || thisOrderGhiChu.isBlank())
                        &&(noNoteCartTtem!=null)){
                    noNoteCartTtem.setSoLuong(noNoteCartTtem.getSoLuong()+1);
                    noNoteCartTtem.setGiaTongMon(noNoteCartTtem.getGiaTungMon()* noNoteCartTtem.getSoLuong());
                    cartDAO.update(noNoteCartTtem);

                }
                else {
                    models.Cart.CartItem newItem = new CartItem();
                    newItem.setUserid(userid);
                    newItem.setMonanid(foodid);
                    newItem.setTenMon(currentFood.getTenMonAn());
                    newItem.setSoLuong(quantity);
                    newItem.setGiaTungMon(currentFood.getGiaMonAn());
                    newItem.setGiaTongMon(currentFood.getGiaMonAn());
                    newItem.setGhiChu(etNote.getText().toString().trim());
                    newItem.setUrl(currentFood.getUrlHinhAnhMonAn());
                    cartDAO.addItem(newItem);
                }







//                if (existingCartItem != null) {
//                    String ghiChuExisted = existingCartItem.getGhiChu();
//
//                    if((ghiChuExisted == null || ghiChuExisted.isBlank())
//                            &&(thisOrderGhiChu==null || thisOrderGhiChu.isBlank())){
//
//                        existingCartItem.setSoLuong(existingCartItem.getSoLuong() + 1);
//                        existingCartItem.setGiaTongMon(existingCartItem.getGiaTungMon() * existingCartItem.getSoLuong());
//                        cartDAO.update(existingCartItem);
//                    }
//                    else {
//                        models.Cart.CartItem newItem = new CartItem();
//                        newItem.setUserid(userid);
//                        newItem.setMonanid(foodid);
//                        newItem.setTenMon(currentFood.getTenMonAn());
//                        newItem.setSoLuong(quantity);
//                        newItem.setGiaTungMon(currentFood.getGiaMonAn());
//                        newItem.setGiaTongMon(currentFood.getGiaMonAn());
//                        newItem.setGhiChu(etNote.getText().toString().trim());
//                        cartDAO.addItem(newItem);
//                    }
//
//                } else {
//                    models.Cart.CartItem newItem = new CartItem();
//                    newItem.setUserid(userid);
//                    newItem.setMonanid(foodid);
//                    newItem.setTenMon(currentFood.getTenMonAn());
//                    newItem.setSoLuong(quantity);
//                    newItem.setGiaTungMon(currentFood.getGiaMonAn());
//                    newItem.setGiaTongMon(currentFood.getGiaMonAn());
//                    newItem.setGhiChu(etNote.getText().toString().trim());
//                    cartDAO.addItem(newItem);
//                }



                Toast.makeText(Chi_tiet_mon.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();

                // Chuyển sang màn hình Cart
                Intent intent = new Intent(Chi_tiet_mon.this, Cart.class);
                startActivity(intent);
            }
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