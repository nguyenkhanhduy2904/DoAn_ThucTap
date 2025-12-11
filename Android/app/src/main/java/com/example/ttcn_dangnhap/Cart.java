package com.example.ttcn_dangnhap;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ttcn_dangnhap.Adapter.CustomCartListAdapter;

import java.util.List;

import models.Cart.CartDAO;
import models.Cart.CartDbHelper;
import models.Cart.CartItem;

public class Cart extends AppCompatActivity {

    List<CartItem> lsCartItem;
    ListView lsView;
    CustomCartListAdapter adapter;

    CartDbHelper cartDbHelper;
    CartDAO cartDAO;

    ImageButton ibtnBack;



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
        int userid = sp.getInt("userid", -1);
        if(userid ==-1){
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            return;
        }
        lsCartItem = cartDAO.getCartByUser(userid);

        addControls();
        addEvents();



    }


    void addControls(){
        lsView= findViewById(R.id.lsViewCart);
        adapter = new CustomCartListAdapter(Cart.this, lsCartItem, cartDAO);
        lsView.setAdapter(adapter);

        ibtnBack = findViewById(R.id.btnBack);






    }

    void addEvents(){
        ibtnBack.setOnClickListener(view -> {
            finish();
        });
    }
}