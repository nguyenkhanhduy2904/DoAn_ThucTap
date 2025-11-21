package com.example.ttcn_dangnhap;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class ttcn_dangky extends AppCompatActivity {

    EditText eTxTFullName, eTxTPhoneNum,eTxTAddress, eTxTUserLoginName,eTxTPassword,eTxTRepeatPassword;
    Spinner spinnerGender;
    MaterialButton mBtnCreateAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ttcn_dangky);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    void addControls(){
        eTxTFullName = findViewById(R.id.eTxTFullName);
        eTxTAddress = findViewById(R.id.eTxTAddress);
        eTxTPhoneNum= findViewById(R.id.eTxTPhoneNum);
        eTxTUserLoginName = findViewById(R.id.eTxTUserLoginName);
        eTxTPassword = findViewById(R.id.eTxTPassword);
        eTxTRepeatPassword = findViewById(R.id.eTxTRepeatPassword);
        mBtnCreateAcc = findViewById(R.id.mBtnCreateAcc);
        spinnerGender= findViewById(R.id.spinner_gender);


    }

    void addEvents(){
        //to be implements

    }
}