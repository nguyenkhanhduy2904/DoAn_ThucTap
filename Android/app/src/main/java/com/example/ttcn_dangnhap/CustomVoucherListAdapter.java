package com.example.ttcn_dangnhap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import models.Voucher;

public class CustomVoucherListAdapter extends BaseAdapter {
    Context context;
    List<Voucher> lsVoucher;
    LayoutInflater inflater;

    public CustomVoucherListAdapter(Context context, List<Voucher> lsVoucher) {
        this.context = context;
        this.lsVoucher = lsVoucher;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lsVoucher.size();
    }

    @Override
    public Object getItem(int i) {
        return lsVoucher.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       //to do later



        return null;
    }
}
