package com.example.ttcn_dangnhap;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class ThongBao {
    public static void showThongBao(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok",(dialogInterface, i) -> {});
        builder.create().show();
    }
}
