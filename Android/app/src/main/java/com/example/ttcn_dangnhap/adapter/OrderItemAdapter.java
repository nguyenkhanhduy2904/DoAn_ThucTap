package com.example.ttcn_dangnhap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.ttcn_dangnhap.R;

import java.util.List;

import models.OrderItemDTO;

public class OrderItemAdapter extends BaseAdapter {
    Context context;
    List<OrderItemDTO> orderItemList;
    LayoutInflater inflater;

    public OrderItemAdapter(Context context, List<OrderItemDTO> orderItemList) {
        this.context = context;
        this.orderItemList = orderItemList;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return orderItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        {

            convertView = inflater.inflate(R.layout.layout_order_item, null);
            TextView tvFoodName = convertView.findViewById(R.id.tvTenmon);
            TextView tvQuantity = convertView.findViewById(R.id.tvSoluong);
            TextView tvNote = convertView.findViewById(R.id.tvGhiChu);


            tvFoodName.setText(orderItemList.get(pos).getTenMon());
            tvQuantity.setText("x" + orderItemList.get(pos).getSoLuong());
            String ghiChu = orderItemList.get(pos).getGhiChu();
            if (ghiChu.equals("null")) {
                tvNote.setText("Không có ghi chú");
            } else {
                if (ghiChu != null && !ghiChu.isEmpty()) {
                    tvNote.setText(ghiChu);
                }
            }


            return convertView;
        }

    }
}
