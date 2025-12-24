package com.example.ttcn_dangnhap.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.ttcn_dangnhap.R;

import java.util.List;

import models.UserDTO;

public class AccountManagementAdapter extends BaseAdapter {

    List<UserDTO> lsUserDTO;
    Context context;
    LayoutInflater inflater;


    public AccountManagementAdapter(Context context, List<UserDTO> lsUserDTO) {
        this.lsUserDTO = lsUserDTO;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lsUserDTO.size();
    }

    @Override
    public Object getItem(int i) {
        return lsUserDTO.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.layout_account, null);

        TextView tvUserid = convertView.findViewById(R.id.txtUserId);
        TextView tvUserRole = convertView.findViewById(R.id.txtRole);
        TextView tvUserStatus = convertView.findViewById(R.id.txtAccStatus);

        TextView tvUserName = convertView.findViewById(R.id.txtUserName);
        TextView tvUserPhone = convertView.findViewById(R.id.txtUserPhone);
        TextView tvUserAddress = convertView.findViewById(R.id.txtUserAddress);

        Button btnNextAction = convertView.findViewById(R.id.btnNextAction);


        UserDTO userDTO = lsUserDTO.get(pos);

        tvUserid.setText("User ID: "+String.valueOf(userDTO.getId()));
        tvUserRole.setText(userDTO.getRole());
        tvUserStatus.setText(userDTO.getTrangThai());
        tvUserName.setText(userDTO.getTenHienThi());
        tvUserPhone.setText(userDTO.getSdt());
        tvUserAddress.setText(userDTO.getDiaChi());

        if(userDTO.getTrangThai().equals("LOCKED")){
            btnNextAction.setText("Mở khóa");
        }
        else if(userDTO.getTrangThai().equals("ACTIVE")){
            btnNextAction.setText("Khóa");

        }




        return convertView;
    }
}
