package com.example.ttcn_dangnhap;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import models.MonAn;
import models.EQuocGia;

public class HomePage extends AppCompatActivity {

    List<MonAn> lsMonAn;
    ListView lsView;
    CustomListAdapter customListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lsMonAn = getListMonAn();
        Log.d("HomePage", "List size: " + lsMonAn.size());
        addControls();

    }

    private List<MonAn> getListMonAn() {
        List<MonAn> lsMonAn = new ArrayList<>();

        lsMonAn.add(new MonAn(
                "1",
                "Phở Bò",
                "Phở bò truyền thống Việt Nam",
                50000L,
                "https://cdn.tgdd.vn/Files/2022/01/25/1412805/cach-nau-pho-bo-nam-dinh-chuan-vi-thom-ngon-nhu-hang-quan-202201250230038502.jpg",
                EQuocGia.VietNam,
                true
        ));

        lsMonAn.add(new MonAn(
                "2",
                "Sushi",
                "Sushi tươi ngon Nhật Bản",
                120000L,
                "https://www.justonecookbook.com/wp-content/uploads/2020/01/Sushi-Rolls-Maki-Sushi-%E2%80%93-Hosomaki-1106-II.jpg",
                EQuocGia.ThaiLan,
                true
        ));

        lsMonAn.add(new MonAn(
                "3",
                "Kimchi",
                "Kimchi cay Hàn Quốc",
                40000L,
                "https://delishglobe.com/wp-content/uploads/2024/12/Kimchi-Fermented-Vegetables.png",
                EQuocGia.HanQuoc,
                true
        ));

        // Add more items if needed

        return lsMonAn;
    }//tao 1 list tam thoi de test

    void addControls(){
        lsView = findViewById(R.id.lsViewItem);
        customListAdapter = new CustomListAdapter(HomePage.this, lsMonAn);
        lsView.setAdapter(customListAdapter);
        setListViewHeight(lsView);
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