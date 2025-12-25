package com.example.ttcn_dangnhap.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ttcn_dangnhap.R;

import org.json.JSONObject;

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
        if(userDTO.getRole().equals("ADMIN")){
            btnNextAction.setEnabled(false);
        }

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

        btnNextAction.setOnClickListener(view -> {
            // Get current status from userDTO
            String currentStatus = userDTO.getTrangThai();
            String newStatus = null;

            if ("LOCKED".equals(currentStatus)) {
                newStatus = "ACTIVE";
            } else if ("ACTIVE".equals(currentStatus)) {
                newStatus = "LOCKED";
            }

            String url = "http://10.0.2.2:8080/api/v1/user/" + userDTO.getId();
            RequestQueue queue = Volley.newRequestQueue(context);

            // Convert UserDTO to JSON
            JSONObject userJson = new JSONObject();
            try {
                userJson.put("tenHienThi", userDTO.getTenHienThi());
                userJson.put("sdt", userDTO.getSdt());
                userJson.put("role", userDTO.getRole());
                userJson.put("diaChi", userDTO.getDiaChi());
                userJson.put("gioiTinh", userDTO.getGioiTinh());
                userJson.put("trangThai", newStatus);
                userJson.put("email", userDTO.getEmail());
            } catch (Exception e) {
                e.printStackTrace();
            }

            final String finalStatus = newStatus;

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.PUT,
                    url,
                    userJson,
                    response -> {
                        try {
                            String status = response.getString("status");
                            String message = response.getString("message");

                            if ("success".equals(status)) {
                                // Update local userDTO
                                userDTO.setTrangThai(finalStatus);

                                // Update button text based on new status
                                if ("ACTIVE".equals(finalStatus)) {
                                    btnNextAction.setText("Khóa"); // button now locks
                                } else if ("LOCKED".equals(finalStatus)) {
                                    btnNextAction.setText("Mở khóa"); // button now unlocks
                                }

                                Toast.makeText(context, "User updated successfully", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(context, "Update failed: " + message, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Response parsing error", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(context, "Error updating user: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
            );

            queue.add(request);
        });

        return convertView;

    }
}
