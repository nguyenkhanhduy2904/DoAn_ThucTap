package com.example.ttcn_dangnhap.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ttcn_dangnhap.R;

import java.util.List;

import models.Cart.CartDAO;
import models.Cart.CartItem;
import okio.Buffer;


public class CustomCartListAdapter extends BaseAdapter {

    private OnItemChangeListener<CartItem> itemChangeListener;

    Context context;
    List<CartItem> lsCartItem;
    LayoutInflater inflater;
    CartDAO cartDAO;



    public CustomCartListAdapter(Context ctx, List<CartItem> lsCartItem, CartDAO cartDAO){
        this.context = ctx;
        this.lsCartItem = lsCartItem;
        inflater = LayoutInflater.from(ctx);
        this.cartDAO = cartDAO;
    }


    @Override
    public int getCount() {
        return lsCartItem.size();
    }

    @Override
    public Object getItem(int i) {
        return lsCartItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_cart, null);
        ImageView imgFood = convertView.findViewById(R.id.imgFood);
        TextView txtFoodName = convertView.findViewById(R.id.tvFoodName);
        TextView txtNote = convertView.findViewById(R.id.tvNoteContent);
        TextView txtPrice = convertView.findViewById(R.id.tvPrice);
        TextView txtQuantity = convertView.findViewById(R.id.tvQuantity);

        Button btnPlus = convertView.findViewById(R.id.btnPlus);
        Button btnMinus = convertView.findViewById(R.id.btnMinus);

        ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);


        CartItem cartItem = lsCartItem.get(pos);

        txtFoodName.setText(cartItem.getTenMon());
        txtNote.setText(cartItem.getGhiChu());
        txtPrice.setText(String.valueOf(cartItem.getGiaTongMon()));
        txtQuantity.setText(String.valueOf(cartItem.getSoLuong()));

        SharedPreferences sp = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userid = sp.getInt("userid", -1);

        btnDelete.setOnClickListener(view -> {
            CartItem existingCartItem = cartDAO.getItemForUser(userid, cartItem.getMonanid());
            if(existingCartItem!=null){
                cartDAO.delete(existingCartItem.getId());
                lsCartItem.remove(cartItem);
                notifyItemChanged();
                notifyDataSetChanged();

            }
        });

        btnPlus.setOnClickListener(view -> {
            CartItem existingCartItem = cartDAO.getItemForUser(userid, cartItem.getMonanid());
            if (existingCartItem != null) {
                int quantity = existingCartItem.getSoLuong() + 1;
                existingCartItem.setSoLuong(quantity);
                long totalPrice = existingCartItem.getGiaTungMon() * quantity;
                existingCartItem.setGiaTongMon(totalPrice);


                cartDAO.update(existingCartItem);

                cartItem.setSoLuong(quantity);
                cartItem.setGiaTongMon(totalPrice);

                txtQuantity.setText(String.valueOf(quantity));
                txtPrice.setText(String.valueOf(totalPrice));


                btnMinus.setEnabled(true);
                notifyItemChanged();
            }
        });




        if(cartItem.getSoLuong()<=1){
            btnMinus.setEnabled(false);
        }
        btnMinus.setOnClickListener(view -> {
            if (cartItem.getSoLuong() > 1) {
                CartItem existingCartItem = cartDAO.getItemForUser(userid, cartItem.getMonanid());
                int quantity = existingCartItem.getSoLuong() - 1;
                existingCartItem.setSoLuong(quantity);
                long totalPrice = existingCartItem.getGiaTungMon() * quantity;
                existingCartItem.setGiaTongMon(totalPrice);

                cartDAO.update(existingCartItem);

                cartItem.setSoLuong(quantity);
                cartItem.setGiaTongMon(totalPrice);

                txtQuantity.setText(String.valueOf(quantity));
                txtPrice.setText(String.valueOf(totalPrice));

                if (quantity <= 1) {
                    btnMinus.setEnabled(false);
                }
                notifyItemChanged();
            }
        });
        return convertView;
    }

    public void setOnItemChangeListener(OnItemChangeListener<CartItem> listener) {
        this.itemChangeListener = listener;
    }

    private void notifyItemChanged() {
        if (itemChangeListener != null) {
            itemChangeListener.onItemChanged(lsCartItem);
        }
    }
}
