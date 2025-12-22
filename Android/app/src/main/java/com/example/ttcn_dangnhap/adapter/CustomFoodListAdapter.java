package com.example.ttcn_dangnhap.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ttcn_dangnhap.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import models.Cart.CartDAO;
import models.Cart.CartItem;
import models.MonAn;

public class CustomFoodListAdapter extends BaseAdapter {

    Context context;
    List<MonAn> lsMonAn;
    LayoutInflater inflater;
    CartDAO cartDAO;
    boolean isAdmin;
    public CustomFoodListAdapter(Context ctx, List<MonAn> lsMonAn, CartDAO cartDAO, boolean isAdmin){
        this.context = ctx;
        this.lsMonAn = lsMonAn;
        inflater = LayoutInflater.from(ctx);
        this.cartDAO = cartDAO;
        this.isAdmin = isAdmin;
    }


    @Override
    public int getCount() {
        Log.d("CustomFoodListAdapter", "getCount called, size = " + lsMonAn.size());
        return lsMonAn.size();
    }

    @Override
    public Object getItem(int i) {
        return lsMonAn.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.layout_fooditem, null);
        ImageView ivFoodImg = convertView.findViewById(R.id.ivFoodImg);
        ImageView ivFlag = convertView.findViewById(R.id.ivFoodFlag);
        TextView txtFoodName = convertView.findViewById(R.id.txtFoodName);
        TextView txtDescription = convertView.findViewById(R.id.txtDescription);
        TextView txtPrice = convertView.findViewById(R.id.txtPrice);
        ImageButton ibtnAdd = convertView.findViewById(R.id.ibtnAdd);

        MonAn monAn = lsMonAn.get(position);
        Picasso.get().load(monAn.getUrlHinhAnhMonAn()).resize(150,150).centerCrop().into(ivFoodImg);
        txtFoodName.setText(monAn.getTenMonAn());
        txtDescription.setText(monAn.getMotaMonAn());
        java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("###,###,###");
        txtPrice.setText(decimalFormat.format(monAn.getGiaMonAn()) + "đ");
        ivFlag.setImageResource(monAn.getQuocGia().getDrawableId());

        if (isAdmin) {
            ibtnAdd.setVisibility(View.GONE); // Nếu là Admin thì ẩn nút đi
        } else {
            ibtnAdd.setVisibility(View.VISIBLE);
            ibtnAdd.setOnClickListener(view -> {
                int foodid = monAn.getIdMonAn();

                SharedPreferences sp = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                int userid = sp.getInt("userid", -1);

                if (userid == -1) {
                    Toast.makeText(context, "User not logged in!", Toast.LENGTH_SHORT).show();
                    return;
                }




                List<CartItem> cartItems = cartDAO.getAllItemWithThisUserAndMonAnId(userid, foodid);

                CartItem noNoteCartTtem = null;
                for(int i =0; i< cartItems.size();i++){
                    CartItem item = cartItems.get(i);
                    if(item.getGhiChu()==null || item.getGhiChu().isBlank()){
                        noNoteCartTtem = item;
                        break;
                    }
                }

                if(noNoteCartTtem!=null){
                    noNoteCartTtem.setSoLuong(noNoteCartTtem.getSoLuong()+1);
                    noNoteCartTtem.setGiaTongMon(noNoteCartTtem.getGiaTungMon() * noNoteCartTtem.getSoLuong());
                    cartDAO.update(noNoteCartTtem);
                }
                else
                {
                    CartItem newItem = new CartItem();
                    newItem.setUserid(userid);
                    newItem.setMonanid(foodid);
                    newItem.setTenMon(monAn.getTenMonAn());
                    newItem.setSoLuong(1);
                    newItem.setGiaTungMon(monAn.getGiaMonAn());
                    newItem.setGiaTongMon(monAn.getGiaMonAn());
                    newItem.setUrl(monAn.getUrlHinhAnhMonAn());
                    cartDAO.addItem(newItem);


                }

                Toast.makeText(context, "Added to cart!", Toast.LENGTH_SHORT).show();


            });
        }

        return convertView;
    }
}
