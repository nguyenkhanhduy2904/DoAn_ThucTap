package com.example.ttcn_dangnhap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import models.MonAn;

public class CustomListAdapter extends BaseAdapter {

    Context context;
    List<MonAn> lsMonAn;
    LayoutInflater inflater;

    public CustomListAdapter(Context ctx, List<MonAn> lsMonAn){
        this.context = ctx;
        this.lsMonAn = lsMonAn;
        inflater = LayoutInflater.from(ctx);

    }


    @Override
    public int getCount() {
        Log.d("CustomListAdapter", "getCount called, size = " + lsMonAn.size());
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
        txtPrice.setText(String.valueOf(monAn.getGiaMonAn()));
        ivFlag.setImageResource(monAn.getQuocGia().getDrawableId());










        return convertView;
    }
}
