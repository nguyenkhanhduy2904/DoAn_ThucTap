package com.example.ttcn_dangnhap.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ttcn_dangnhap.Adapter.CartAdapter;
import com.example.ttcn_dangnhap.R;

import java.util.List;

import models.OrderItemDTO;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    Context context;
    List<OrderItemDTO> ds;
    public OrderDetailAdapter(Context context, List<OrderItemDTO> ds) {
        this.context = context;
        this.ds = ds;
    }

    @NonNull
    @Override
    public OrderDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_cart_tt,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.ViewHolder holder, int position) {
        OrderItemDTO item = ds.get(position);
        holder.tvFoodname.setText(item.getTenMon());
        holder.tvSl.setText("x" + item.getSoLuong());
        if (item.getGhiChu() != null && !item.getGhiChu().isEmpty()) {
            holder.tvNote.setText(item.getGhiChu());
        } else {
            holder.tvNote.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return ds==null?0:ds.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodname,tvSl,tvNote;
        ImageView imgFood;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodname=itemView.findViewById(R.id.tvFoodName);
            tvSl=itemView.findViewById(R.id.tvSl);
            tvNote=itemView.findViewById(R.id.tvNote);
        }
    }

}
