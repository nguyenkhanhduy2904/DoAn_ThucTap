package com.example.ttcn_dangnhap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ttcn_dangnhap.Adapter.CustomCartListAdapter;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import models.Cart.CartDAO;
import models.Cart.CartDbHelper;
import models.Cart.CartItem;
import models.MonAn;

public class Cart extends AppCompatActivity {

    List<CartItem> lsCartItem;
    ListView lsViewCart;
    CustomCartListAdapter adapter;

    CartDbHelper cartDbHelper;
    CartDAO cartDAO;

    ImageButton ibtnBack;

    TextView txtPrice;

    MaterialButton btnConfirm;
    int userid;


    private static final int REQUEST_CODE_FOR_CART_UPDATE = 100;//request code cho cart, new ThanhToan thanh cong thi xoa toan bo cart di

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

        //sqlite
        cartDbHelper = new CartDbHelper(Cart.this);
        cartDAO = new CartDAO(cartDbHelper);

        SharedPreferences sp = this.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userid = sp.getInt("userid", -1);
        if(userid ==-1){
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            return;
        }
        lsCartItem = cartDAO.getCartByUser(userid);

        addControls();
        addEvents();



    }


    void addControls(){
        lsViewCart= findViewById(R.id.lsViewCart);
        adapter = new CustomCartListAdapter(Cart.this, lsCartItem, cartDAO);
        lsViewCart.setAdapter(adapter);

        ibtnBack = findViewById(R.id.btnBack);

        txtPrice = findViewById(R.id.tvPrice);

        btnConfirm = findViewById(R.id.btnConfirm);

    }

    void addEvents(){
        ibtnBack.setOnClickListener(view -> {
            finish();
        });
        long totalPrice =0;
        for(int i =0; i< lsCartItem.size(); i++){
            CartItem cartItem = lsCartItem.get(i);
            totalPrice = totalPrice + cartItem.getGiaTongMon();
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");






        txtPrice.setText(formatter.format(totalPrice));
        lsViewCart.setOnItemClickListener((adapterView, view, i, l) -> {
            CartItem item = lsCartItem.get(i);
            MonAn monAn = new MonAn();
            monAn.setIdMonAn(item.getMonanid());
            monAn.setTenMonAn(item.getTenMon());
            monAn.setGiaMonAn(item.getGiaTungMon());
            Intent intent = new Intent(Cart.this, Chi_tiet_mon.class);
            intent.putExtra("monAn", monAn);
            startActivity(intent);
        });


        adapter.setOnItemChangeListener(updatedList -> {
            long totalPriceReCalc =0;
            for(int i =0; i< lsCartItem.size(); i++){
                CartItem cartItem = lsCartItem.get(i);
                totalPriceReCalc = totalPriceReCalc + cartItem.getGiaTongMon();
            }
            txtPrice.setText(formatter.format(totalPriceReCalc));
        });

        btnConfirm.setOnClickListener(view -> {
            Intent intent = new Intent(this, ThanhToan.class);
            intent.putExtra("CART_LIST", (Serializable) lsCartItem);
            String totalPriceStr = txtPrice.getText().toString().trim();
            intent.putExtra("TOTAL_PRICE", totalPriceStr);
            startActivityForResult(intent, REQUEST_CODE_FOR_CART_UPDATE);

            });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_FOR_CART_UPDATE && resultCode == RESULT_OK){
            //ThanhToan thanh cong, xoa toan bo cart di
            cartDAO.clearCart(userid);
            lsCartItem.clear();
            adapter.notifyDataSetChanged();
            txtPrice.setText("0");
//            Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();

        }
    }
}