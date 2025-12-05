package com.example.ttcn_dangnhap;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class ThongBao {
    public static void showThongBao(Context context, String title, String message, Runnable action) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok",(dialogInterface, i) -> {
            dialogInterface.dismiss();
            if(action!=null)
            {
                action.run();
            }
        });
        builder.create().show();
    }
}
