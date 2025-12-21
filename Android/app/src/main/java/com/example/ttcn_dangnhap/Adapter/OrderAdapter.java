package com.example.ttcn_dangnhap.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ttcn_dangnhap.R;

import java.text.SimpleDateFormat;
import java.util.List;

import models.OrderDTO;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ADMIN = 1;
    private static final int TYPE_USER_TRACKING = 2;
    private static final int TYPE_USER_HISTORY = 3;

    Context context;
    List<OrderDTO> ds;
    int mode;
    OnOrderActionListener listener;

    public OrderAdapter(Context context, List<OrderDTO> ds, int mode, OnOrderActionListener listener) {
        this.context = context;
        this.ds = ds;
        this.mode = mode;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return mode;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_ADMIN) {
            View v = inflater.inflate(R.layout.layout_admin_order, parent, false);
            return new AdminViewHolder(v);
        } else if (viewType == TYPE_USER_TRACKING) {
            View v = inflater.inflate(R.layout.layout_theo_doi, parent, false);
            return new UserTrackingViewHolder(v);
        } else {
            View v = inflater.inflate(R.layout.layout_lich_su, parent, false);
            return new UserHistoryViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderDTO order = ds.get(position);
        if (holder instanceof AdminViewHolder) {
            bindAdmin((AdminViewHolder) holder, order);
        } else if (holder instanceof UserTrackingViewHolder) {
            bindTracking((UserTrackingViewHolder) holder, order);
        } else if (holder instanceof UserHistoryViewHolder) {
            bindHistory((UserHistoryViewHolder) holder, order);
        }
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public interface OnOrderActionListener {
        void onStatusChange(int orderId, String newStatus);
    }

    private void bindAdmin(AdminViewHolder holder, OrderDTO order) {
        holder.tvOrderId.setText("Mã đơn hàng: #" + order.getId());
        holder.tvCustomerName.setText("Khách: " + order.getTenKhachHang());
        holder.tvTotalPrice.setText(order.getTongTien() + "đ");
        holder.rvItems.setLayoutManager(new LinearLayoutManager(context));
        holder.rvItems.setAdapter(new OrderDetailAdapter(context, order.getItems()));

        String status = order.getTrangThaiDonHang();

        if ("Pending".equalsIgnoreCase(status)) {
            holder.layoutActionPending.setVisibility(View.VISIBLE);
            holder.btnChangeStatus.setVisibility(View.GONE);

            holder.btnConfirm.setText("Xác nhận");
            holder.btnConfirm.setOnClickListener(v -> listener.onStatusChange(order.getId(), "Confirm"));

            holder.btnReject.setText("Từ chối");
            holder.btnReject.setOnClickListener(v -> listener.onStatusChange(order.getId(), "Refuse"));

        } else if ("Confirm".equalsIgnoreCase(status)) {
            holder.layoutActionPending.setVisibility(View.GONE);
            holder.btnChangeStatus.setVisibility(View.VISIBLE);

            holder.btnChangeStatus.setText("Giao cho shipper");
            holder.btnChangeStatus.setOnClickListener(v -> listener.onStatusChange(order.getId(), "Delivering"));

        } else if ("Delivering".equalsIgnoreCase(status)) {
            holder.layoutActionPending.setVisibility(View.GONE);
            holder.btnChangeStatus.setVisibility(View.VISIBLE);

            holder.btnChangeStatus.setText("Xác nhận đã giao xong");
            holder.btnChangeStatus.setOnClickListener(v -> listener.onStatusChange(order.getId(), "Finish"));

        } else {
            holder.layoutActionPending.setVisibility(View.GONE);
            holder.btnChangeStatus.setVisibility(View.GONE);
        }
    }

    private void bindTracking(UserTrackingViewHolder holder, OrderDTO order) {
        holder.txtOrderId.setText("Đơn hàng #" + order.getId());
        holder.txtStatus.setText(translateStatus(order.getTrangThaiDonHang()));
        holder.txtTotalPrice.setText(order.getTongTien() + "đ");

        if (order.getTrangThaiDonHang().equals("Pending"))
            holder.txtStatus.setTextColor(Color.RED);
        else
            holder.txtStatus.setTextColor(Color.BLUE);

        holder.rvCartItems.setLayoutManager(new LinearLayoutManager(context));
        holder.rvCartItems.setAdapter(new OrderDetailAdapter(context, order.getItems()));
    }

    private void bindHistory(UserHistoryViewHolder holder, OrderDTO order) {
        holder.tvOrderId.setText("#" + order.getId());
        holder.tvOrderStatus.setText(translateStatus(order.getTrangThaiDonHang()));
        holder.tvTotalPrice.setText("Tổng tiền: " + order.getTongTien() + "đ");

        // Format ngày tháng
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            if (order.getThoiGianTao() != null) {
                holder.tvCompletionDate.setText(sdf.format(order.getThoiGianTao()));
            }
        } catch (Exception e) {
            holder.tvCompletionDate.setText("");
        }

        holder.rvCartItems.setLayoutManager(new LinearLayoutManager(context));
        holder.rvCartItems.setAdapter(new OrderDetailAdapter(context, order.getItems()));
    }

    private String translateStatus(String status) {
        if (status == null) return "";
        switch (status) {
            case "Pending":
                return "Đang chờ xác nhận";
            case "Confirm":
                return "Đang chuẩn bị món";
            case "Delivering":
                return "Đang giao hàng";
            case "Finish":
                return "Hoàn thành";
            case "Refuse":
                return "Đã từ chối";
            case "Cancel":
                return "Đã hủy";
            default:
                return status;
        }
    }


    public class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvCustomerName, tvTotalPrice;
        RecyclerView rvItems;
        LinearLayout layoutActionPending;
        Button btnReject, btnConfirm, btnChangeStatus;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            rvItems = itemView.findViewById(R.id.item_cart_tt);
            layoutActionPending = itemView.findViewById(R.id.layoutActionPending);
            btnReject = itemView.findViewById(R.id.btnReject);
            btnConfirm = itemView.findViewById(R.id.btnConfirm);
            btnChangeStatus = itemView.findViewById(R.id.btnChangeStatus);
        }
    }

    public class UserTrackingViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderId, txtStatus, txtTotalPrice;
        RecyclerView rvCartItems;

        public UserTrackingViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtTotalPrice = itemView.findViewById(R.id.txtTotalPrice);
            rvCartItems = itemView.findViewById(R.id.rvCartItems);
        }
    }

    public class UserHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderStatus, tvCompletionDate, tvTotalPrice;
        RecyclerView rvCartItems;

        public UserHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvCompletionDate = itemView.findViewById(R.id.tvCompletionDate);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            rvCartItems = itemView.findViewById(R.id.rvCartItems);
        }
    }
}