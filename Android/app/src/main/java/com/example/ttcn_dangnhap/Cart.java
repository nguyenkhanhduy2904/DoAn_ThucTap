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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ttcn_dangnhap.adapter.CustomCartListAdapter;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;

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
        txtPrice.setText(String.valueOf(totalPrice));
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
            txtPrice.setText(String.valueOf(totalPriceReCalc));
        });

        btnConfirm.setOnClickListener(view -> {
//            String url = "http://10.0.2.2:8080/api/v1/orders";

            JSONArray cartArray = new JSONArray();
            for(int i =0; i< lsCartItem.size(); i++){
                try{
                    CartItem item = lsCartItem.get(i);
                    JSONObject jsonItem = new JSONObject();
                    jsonItem.put("tenMon",item.getTenMon() );
                    jsonItem.put("soLuong", item.getSoLuong());
                    jsonItem.put("ghiChu", item.getGhiChu());
                    jsonItem.put("giaTungMon", item.getGiaTungMon());
                    jsonItem.put("giaTongMon", item.getGiaTongMon());
                    jsonItem.put("monanid", item.getMonanid());

                    cartArray.put(jsonItem);

//                    Intent intent = new Intent(Cart.this, );
                }
                catch (Exception e){
                    Toast.makeText(Cart.this, e.getMessage(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }

        });

    }
}