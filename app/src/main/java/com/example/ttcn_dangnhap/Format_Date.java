package com.example.ttcn_dangnhap;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Format_Date {
    static SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
    public static String formatDate(Date date){
        return sdfDate.format(date);
    }
}
