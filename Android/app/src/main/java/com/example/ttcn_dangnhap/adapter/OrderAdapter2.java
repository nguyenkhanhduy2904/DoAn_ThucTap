package com.example.ttcn_dangnhap.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ttcn_dangnhap.Network.APICallback;
import com.example.ttcn_dangnhap.OrderDetail;
import com.example.ttcn_dangnhap.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import models.OrderDTO;

public class OrderAdapter2 extends BaseAdapter {
    Context context;
    List<OrderDTO> orderList;
    LayoutInflater inflater;
    ListView outerListView;
    private APICallback<OrderDTO> statusChangeCallback;

    public OrderAdapter2(Context context, List<OrderDTO> orderList, ListView outerListView) {
        this.context = context;
        this.orderList = orderList;
        this.outerListView = outerListView;
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
    public View getView(int pos, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.layout_order_tracking, null);
        TextView tvOrderId = convertView.findViewById(R.id.txtOrderId);
        TextView tvOrderStatus = convertView.findViewById(R.id.txtOrderStatus);
        TextView tvTotalPrice = convertView.findViewById(R.id.txtTotalPrice);
        TextView tvPaymentStatus = convertView.findViewById(R.id.txtPaymentStatus);
//        TextView tvOrderTime = convertView.findViewById(R.id.txtOrderTime);
//        ListView lvOrderItems = convertView.findViewById(R.id.lvOrderItems);

        Button btnCancel = convertView.findViewById(R.id.btnCancel);
        Button btnNextAction = convertView.findViewById(R.id.btnNextAction);
        btnNextAction.setText("Xem chi tiết");


        OrderDTO order = orderList.get(pos);

//        Date orderTime = order.getThoiGianTao();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        String orderTimeStr = sdf.format(orderTime);

        tvOrderId.setText("Order ID: " + order.getId());
        tvOrderStatus.setText(order.getTrangThaiDonHang());
        tvTotalPrice.setText(order.getTongTien() + " VND");
        tvPaymentStatus.setText(order.getTrangThaiThanhToan());

        if(order.getTrangThaiDonHang().equals("Finished")||order.getTrangThaiDonHang().equals("Cancelled")||order.getTrangThaiDonHang().equals("Refused")){
            btnCancel.setVisibility(View.GONE);
        }


        btnNextAction.setOnClickListener(view -> {
            Intent intent = new Intent(context, OrderDetail.class);
            intent.putExtra("orderItems", (java.io.Serializable) order.getItems());
            intent.putExtra("order", order);
            context.startActivity(intent);

        });
        btnCancel.setOnClickListener(view -> {

            changeOrderStatus("Cancelled", order.getId(), order);

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

    public void updateData(List<OrderDTO> newList) {
        this.orderList.clear();
        this.orderList.addAll(newList);
        notifyDataSetChanged();
    }



    public static void setListViewHeight(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) return;

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }



    public static void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
        Log.d("OrderAdapter2", "Set ListView height to " + params.height);
    }

}
