package com.example.ttcn_dangnhap;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VoucherList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voucher_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void addControl(){

    }

    void addEvents(){


    }







    void setListViewHeight(ListView lsView){
        ListAdapter adapter = lsView.getAdapter();
        if(adapter == null) return;

        int totalHeight = 0;
        for(int i = 0 ; i< adapter.getCount(); i++){

            View listItem = adapter.getView(i, null, lsView);
            listItem.measure(0,0);
            totalHeight+=listItem.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = lsView.getLayoutParams();
        params.height = totalHeight + (lsView.getDividerHeight() * (adapter.getCount()-1));
        lsView.setLayoutParams(params);
        lsView.requestLayout();
    }
}