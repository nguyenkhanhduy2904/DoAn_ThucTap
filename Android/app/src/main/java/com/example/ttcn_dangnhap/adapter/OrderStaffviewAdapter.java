package com.example.ttcn_dangnhap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ttcn_dangnhap.Network.APICallback;
import com.example.ttcn_dangnhap.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import models.OrderDTO;
import models.OrderItemDTO;

public class OrderStaffviewAdapter extends BaseAdapter {
    Context context;
    List<OrderDTO> orderList;
    LayoutInflater inflater;
    private APICallback<OrderDTO> statusChangeCallback;
    public OrderStaffviewAdapter(Context context, List<OrderDTO> orderList) {
        this.context = context;
        this.orderList = orderList;
        this.inflater = LayoutInflater.from(context);
    }
    public void setStatusChangeCallback(APICallback<OrderDTO> callback) {
        this.statusChangeCallback = callback;
    }


    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public android.view.View getView(int pos, android.view.View convertView, android.view.ViewGroup parent) {
        convertView = inflater.inflate(R.layout.layout_order_staffview, null);
        TextView tvOrderId = convertView.findViewById(R.id.txtOrderId);

        TextView tvTime = convertView.findViewById(R.id.txtTime);
        TextView tvTotalPrice = convertView.findViewById(R.id.txtTotalPrice);
        Button btnCancel = convertView.findViewById(R.id.btnCancel);
        Button btnNextAction = convertView.findViewById(R.id.btnNextAction);


        OrderDTO order = orderList.get(pos);

        Date orderTime = order.getThoiGianTao();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String orderTimeStr = sdf.format(orderTime);

        tvOrderId.setText("Order ID: " + order.getId());
        tvTime.setText(orderTimeStr);
        tvTotalPrice.setText(order.getTongTien().toString() + " VND");

        LinearLayout itemContainer = convertView.findViewById(R.id.itemContainer);
        itemContainer.removeAllViews(); // Clear recycled views

        for (OrderItemDTO item : order.getItems()) {
            View itemView = inflater.inflate(R.layout.layout_order_item, itemContainer, false);
            TextView tvName = itemView.findViewById(R.id.tvTenmon);
            TextView tvQuantity = itemView.findViewById(R.id.tvSoluong);
            tvName.setText(item.getTenMon());
            tvQuantity.setText("x" + item.getSoLuong());
            itemContainer.addView(itemView);
        }

        String orderStatus = order.getTrangThaiDonHang();
        switch (orderStatus){
            case "Pending":
                btnNextAction.setText("Nhận đơn");
                btnNextAction.setEnabled(true);
                btnCancel.setEnabled(true);
                break;
            case "Confirmed":
                btnNextAction.setText("Giao hàng");
                btnNextAction.setEnabled(true);
                btnCancel.setEnabled(false);
                break;
            case "Delivering":
                btnNextAction.setText("Đã giao hàng");
                btnNextAction.setEnabled(true);
                btnCancel.setEnabled(false);
                break;
            case "Finished":
//                btnNextAction.setText("Complete Order");
                btnNextAction.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
                break;

            case "Cancelled":
                btnNextAction.setText("Order Cancelled");
                btnNextAction.setEnabled(false);
                btnCancel.setEnabled(false);
                break;
            default:
                btnNextAction.setText("Unknown Status");
                btnNextAction.setEnabled(false);
                btnCancel.setEnabled(false);
                break;
        }





        btnCancel.setOnClickListener(view -> {
            // Handle cancel order action
        });
        btnNextAction.setOnClickListener(view -> {
            String currentOrderStatus = order.getTrangThaiDonHang();
            switch (currentOrderStatus){
                case "Pending":
                    changeOrderStatus("Confirmed", order.getId(), order);
                    break;
                case "Confirmed":
                    changeOrderStatus("Delivering", order.getId(), order);
                    break;
                case "Delivering":
                    changeOrderStatus("Finished", order.getId(), order);
                    if(order.getPhuongThucThanhToan().equals("COD")){
                        changePaymentStatus("Confirmed", order.getId(), order );

                    }
                    break;
            }
        });




        return convertView;
    }

    void changeOrderStatus(String newStatus, int orderId, OrderDTO order) {
        String url = "http://10.0.2.2:8080/api/v1/orders/update-order-status/" + orderId
                + "?status=" + newStatus;

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(
                Request.Method.PUT,
                url,
                response -> {
                    Toast.makeText(context, "Status updated: " + newStatus, Toast.LENGTH_SHORT).show();

                    // update the local order object
                    order.setTrangThaiDonHang(newStatus);

                    // notify Activity
                    if (statusChangeCallback != null) {
                        statusChangeCallback.onSuccess(order);
                    }

                },
                error -> Toast.makeText(context, "Error updating status", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(request);
    }

    void changePaymentStatus(String newStatus, int orderId, OrderDTO order) {
        String url = "http://10.0.2.2:8080/api/v1/orders/update-payment-status/" + orderId
                + "?status=" + newStatus;

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(
                Request.Method.PUT,
                url,
                response -> {
                    Toast.makeText(context, "Status updated: " + newStatus, Toast.LENGTH_SHORT).show();

                    // update the local order object
                    order.setTrangThaiThanhToan(newStatus);

                    // notify Activity
                    if (statusChangeCallback != null) {
                        statusChangeCallback.onSuccess(order);
                    }

                },
                error -> Toast.makeText(context, "Error updating status", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(request);
    }


}
