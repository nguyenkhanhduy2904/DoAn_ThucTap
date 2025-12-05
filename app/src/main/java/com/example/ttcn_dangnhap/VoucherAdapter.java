package com.example.ttcn_dangnhap;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class VoucherAdapter extends ArrayAdapter<Voucher> {
    private Activity context;
    private int res;
    private List<Voucher> dsvc;
    public VoucherAdapter(Activity context, int res, List<Voucher> dsvc) {
        super(context, res,dsvc);
        this.context = context;
        this.res = res;
        this.dsvc = dsvc;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.res, null);
        ImageView imgVoucher = item.findViewById(R.id.img_voucher);
        TextView txtTen = item.findViewById(R.id.tv_ten_voucher);
        TextView txtMoTa = item.findViewById(R.id.tv_mo_ta);
        TextView txtLoai = item.findViewById(R.id.tv_loai_uu_dai);
        TextView txtNgay = item.findViewById(R.id.tv_ngay_ap_dung);
        Voucher voucher = dsvc.get(position);
        if (voucher != null) {
            txtTen.setText(voucher.getTenVoucher());
            txtMoTa.setText(voucher.getMoTa());
            txtLoai.setText("Loại: "+voucher.getLoaiUuDai());
            txtNgay.setText("Ngày: " + voucher.getNgaybd()+" - "+voucher.getNgaykt());
            if (voucher.getAnhUri() != null && !voucher.getAnhUri().isEmpty()) {
                try {
                    imgVoucher.setImageURI(Uri.parse(voucher.getAnhUri()));
                } catch (Exception e) {
                    imgVoucher.setImageResource(R.drawable.logo); // Ảnh lỗi thì hiện logo
                }
            } else {
                imgVoucher.setImageResource(R.drawable.logo); // Không có ảnh thì hiện logo
            }
        }

        return item;
    }
}
