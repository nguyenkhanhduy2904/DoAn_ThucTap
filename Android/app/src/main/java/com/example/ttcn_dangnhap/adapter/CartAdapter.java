package com.example.ttcn_dangnhap.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ttcn_dangnhap.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import models.Cart.CartItem;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    List<models.Cart.CartItem> cartList;

    // Interface để gửi tín hiệu cập nhật tổng tiền về Activity
    public interface OnCartChangeListener {
        void onCartChanged();
    }

    OnCartChangeListener listener;

    public CartAdapter(Context context, List<models.Cart.CartItem> cartList, OnCartChangeListener listener) {
        this.context = context;
        this.cartList = cartList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = cartList.get(position);

        // Set dữ liệu
        holder.tvFoodName.setText(item.getTenMon());
        holder.tvQuantity.setText(String.valueOf(item.getSoLuong()));

        // Xử lý ghi chú (nếu null hoặc rỗng thì ẩn hoặc hiện text mặc định)
        if(item.getGhiChu() == null || item.getGhiChu().isEmpty()) {
            holder.tvNoteContent.setText("Không có ghi chú");
        } else {
            holder.tvNoteContent.setText(item.getGhiChu());
        }

        // Tính giá và Format
        long price = item.getGiaTungMon() * item.getSoLuong();
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        holder.tvPrice.setText(formatter.format(price));

        // Load ảnh bằng Picasso
//        Picasso.get().load(item..getUrlHinhAnhMonAn())
//                .placeholder(R.drawable.ic_launcher_background) // Thay bằng ảnh mặc định của bạn nếu có
//                .error(R.drawable.ic_launcher_background)
//                .fit().centerCrop()
//                .into(holder.imgFood);

        // Sự kiện nút Tăng (+)
        holder.btnPlus.setOnClickListener(v -> {
            int newQuantity = item.getSoLuong() + 1;
            item.setSoLuong(newQuantity);
            // Cập nhật lại item hiện tại
            notifyItemChanged(holder.getAdapterPosition());
            // Gọi Activity tính lại tổng tiền
            listener.onCartChanged();
        });

        // Sự kiện nút Giảm (-)
        holder.btnMinus.setOnClickListener(v -> {
            if (item.getSoLuong() > 1) {
                int newQuantity = item.getSoLuong() - 1;
                item.setSoLuong(newQuantity);
                notifyItemChanged(holder.getAdapterPosition());
                listener.onCartChanged();
            }
        });

        // Sự kiện nút Xóa (Thùng rác)
        holder.btnDelete.setOnClickListener(v -> {
            cartList.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
            notifyItemRangeChanged(holder.getAdapterPosition(), cartList.size());
            listener.onCartChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood, btnDelete;
        TextView tvFoodName, tvNoteContent, tvPrice, tvQuantity;
        Button btnMinus, btnPlus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFood);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvNoteContent = itemView.findViewById(R.id.tvNoteContent);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
        }
    }
}
