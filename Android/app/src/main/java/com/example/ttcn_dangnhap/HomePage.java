package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ttcn_dangnhap.Network.APICallback;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import models.MonAn;
import models.EQuocGia;

public class HomePage extends AppCompatActivity {

    List<MonAn> lsMonAn;
    ListView lsView;
    CustomFoodListAdapter customFoodListAdapter;

    //nav bar button
    LinearLayout ibtnHome, ibtnVoucher, ibtnOrder, ibtnAccount;
    ImageButton  ibtnMenu;




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
        lsMonAn = new ArrayList<>();

        addControls();
        addEvents();

        GETAllItem(new APICallback<List<MonAn>>() {
            @Override
            public void onSuccess(List<MonAn> result) {
                // replace data and refresh adapter
                lsMonAn.clear();
                lsMonAn.addAll(result);
                // notify adapter
                customFoodListAdapter.notifyDataSetChanged();
                // recalc list height because items changed
                setListViewHeight(lsView);
                Log.d("HomePage", "List size (callback): " + lsMonAn.size());


            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(HomePage.this, errorMessage, Toast.LENGTH_LONG).show();

            }
        });



    }

//    private List<MonAn> getListMonAn() {
//        List<MonAn> lsMonAn = new ArrayList<>();
//
//        lsMonAn.add(new MonAn(
//                1,
//                "Phở Bò",
//                "Phở bò truyền thống Việt Nam",
//                50000L,
//                "https://cdn.tgdd.vn/Files/2022/01/25/1412805/cach-nau-pho-bo-nam-dinh-chuan-vi-thom-ngon-nhu-hang-quan-202201250230038502.jpg",
//                EQuocGia.VietNam,
//                true
//        ));
//
//        lsMonAn.add(new MonAn(
//                2,
//                "Sushi",
//                "Sushi tươi ngon Nhật Bản",
//                120000L,
//                "https://www.justonecookbook.com/wp-content/uploads/2020/01/Sushi-Rolls-Maki-Sushi-%E2%80%93-Hosomaki-1106-II.jpg",
//                EQuocGia.ThaiLan,
//                true
//        ));
//
//        lsMonAn.add(new MonAn(
//                3,
//                "Kimchi",
//                "Kimchi cay Hàn Quốc",
//                40000L,
//                "https://delishglobe.com/wp-content/uploads/2024/12/Kimchi-Fermented-Vegetables.png",
//                EQuocGia.HanQuoc,
//                true
//        ));
//
//        // Add more items if needed
//
//        return lsMonAn;
//    }//tao 1 list tam thoi de test

    private void GETAllItem(APICallback<List<MonAn>> callback){
        String url = "http://10.0.2.2:8080/api/v1/monan";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        String status = response.getString("status");
                        String message = response.getString("message");
                        if(status.equals("success")){
                            JSONArray dataArray = response.getJSONArray("data");
                            List<MonAn> resultList = new ArrayList<>();
                            for(int i =0; i<dataArray.length(); i++){
                                JSONObject obj = dataArray.getJSONObject(i);

                                String quocGia = obj.getString("quocGia");
                                EQuocGia eQuocGia = EQuocGia.StringtoEnum(quocGia);



                                MonAn monAn = new MonAn(
                                        obj.getInt("id"),
                                        obj.getString("tenMonAn"),
                                        obj.getString("moTa"),
                                        obj.getLong("gia"),
                                        "http://10.0.2.2:8080"+obj.getString("hinhAnhURL"),
                                        eQuocGia,
                                        (obj.getString("trangThai")).equals("ACTIVE")
                                );

                                resultList.add(monAn);
                            }

                            callback.onSuccess(resultList);


                        }else{
                            callback.onError("GET food data failed: " + message);
                        }

                    }catch (Exception e){
                        callback.onError("Error converting request: " + e.getMessage());
                    }

                },error -> {
            if (error.networkResponse != null && error.networkResponse.data != null) {
                try {
                    String errorJson = new String(error.networkResponse.data, "UTF-8");
                    JSONObject obj = new JSONObject(errorJson);

                    callback.onError(obj.getString("message"));

                } catch (Exception e) {
                    callback.onError("Error parsing error response: " + e.getMessage());
                }
            } else {
                callback.onError("Connection error");
            }
        }
        );

        queue.add(request);





    }


    void addControls(){
        lsView = findViewById(R.id.lsViewItem);
        customFoodListAdapter = new CustomFoodListAdapter(HomePage.this, lsMonAn);
        lsView.setAdapter(customFoodListAdapter);
        setListViewHeight(lsView);

        //set image button cho thanh nav
        View navBar = findViewById(R.id.navBar);
        ibtnHome = navBar.findViewById(R.id.ibtnHome);
        ibtnVoucher = navBar.findViewById(R.id.ibtnVoucher);
        ibtnOrder = navBar.findViewById(R.id.ibtnOrder);
        ibtnAccount = navBar.findViewById(R.id.ibtnAccount);
        ibtnMenu = navBar.findViewById(R.id.ibtnMenu);
    }


    void addEvents(){
        ibtnHome.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, HomePage.class);
            startActivity(intent);
            overridePendingTransition(0,0);
            Toast.makeText(HomePage.this, "Clicked Home", Toast.LENGTH_SHORT).show();
        });
        ibtnVoucher.setOnClickListener(view -> {


            Toast.makeText(HomePage.this, "Clicked Voucher", Toast.LENGTH_SHORT).show();
        });
        ibtnOrder.setOnClickListener(view -> {

            Toast.makeText(HomePage.this, "Clicked Order", Toast.LENGTH_SHORT).show();
        });
        ibtnAccount.setOnClickListener(view -> {

            Toast.makeText(HomePage.this, "Clicked Account", Toast.LENGTH_SHORT).show();
        });
        ibtnMenu.setOnClickListener(view -> {

            Toast.makeText(HomePage.this, "Clicked Order", Toast.LENGTH_SHORT).show();
        });
        lsView.setOnItemClickListener((adapterView, view, i, l) -> {
            MonAn monAnDuocChon = lsMonAn.get(i);
            Intent intent = new Intent(HomePage.this, Chi_tiet_mon.class);
            intent.putExtra("monAn", monAnDuocChon);
            startActivity(intent);
        });
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