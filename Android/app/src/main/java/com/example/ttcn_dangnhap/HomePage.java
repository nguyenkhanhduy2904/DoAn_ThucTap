package com.example.ttcn_dangnhap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.example.ttcn_dangnhap.Adapter.CustomFoodListAdapter;
import com.example.ttcn_dangnhap.Network.APICallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import models.Cart.CartDAO;
import models.Cart.CartDbHelper;
import models.MonAn;
import models.EQuocGia;

public class HomePage extends AppCompatActivity {


    List<MonAn> listDisplayMonAn;


    List<MonAn> lsAllMonAn;
    ListView lsView;
    CustomFoodListAdapter customFoodListAdapter;
    ImageView cart;
    //nav bar button
    LinearLayout ibtnHome, ibtnVoucher, ibtnOrder, ibtnAccount;
    ImageButton  ibtnMenu, ibtnCart;

    //linear layout flag btn
    LinearLayout lnBestSell,lnVN, lnTL, lnHQ, lnTQ;

    CartDbHelper cartDbHelper;
    CartDAO cartDAO;







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

        //sqlite
        cartDbHelper = new CartDbHelper(HomePage.this);
        cartDAO = new CartDAO(cartDbHelper);

        //
        lsAllMonAn = new ArrayList<>();
        listDisplayMonAn = new ArrayList<>();


        addControls();
        addEvents();

        GETAllItem(new APICallback<List<MonAn>>() {
            @Override
            public void onSuccess(List<MonAn> result) {
                lsAllMonAn.clear();            // refresh data source
                lsAllMonAn.addAll(result);     // fill all items

                listDisplayMonAn.clear();      // refresh UI list
                listDisplayMonAn.addAll(lsAllMonAn);

                customFoodListAdapter.notifyDataSetChanged();
                setListViewHeight(lsView);

                Log.d("HomePage", "List size: " + listDisplayMonAn.size());


            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(HomePage.this, errorMessage, Toast.LENGTH_LONG).show();

            }
        });



    }



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
        customFoodListAdapter = new CustomFoodListAdapter(HomePage.this, listDisplayMonAn, cartDAO,false);
        lsView.setAdapter(customFoodListAdapter);
        setListViewHeight(lsView);
        //set image button cho thanh nav
        View navBar = findViewById(R.id.navBar);
        ibtnHome = navBar.findViewById(R.id.ibtnHome);
        ibtnVoucher = navBar.findViewById(R.id.ibtnVoucher);
        ibtnOrder = navBar.findViewById(R.id.ibtnOrder);
        ibtnAccount = navBar.findViewById(R.id.ibtnAccount);
        ibtnMenu = navBar.findViewById(R.id.ibtnMenu);
        ibtnCart = findViewById(R.id.ibtnCart);

        //set linear layout flag
        lnVN = findViewById(R.id.layoutVietNam);
        lnTL = findViewById(R.id.layoutThaiLand);
        lnHQ = findViewById(R.id.layoutSKorea);
        lnTQ = findViewById(R.id.layoutChina);
        lnBestSell = findViewById(R.id.layoutBestSell);
    }


    void addEvents(){
        lnVN.setOnClickListener(view -> {
            List<MonAn> resultList = new ArrayList<>();
            for(int i=0; i<lsAllMonAn.size(); i++){
                MonAn item = lsAllMonAn.get(i);
                if(item.getQuocGia() == EQuocGia.VietNam){

                    resultList.add(item);
                }
            }
            listDisplayMonAn.clear();      // refresh UI list
            listDisplayMonAn.addAll(resultList);
            customFoodListAdapter.notifyDataSetChanged();
            setListViewHeight(lsView);

        });
        lnTL.setOnClickListener(view -> {
            List<MonAn> resultList = new ArrayList<>();
            for(int i=0; i<lsAllMonAn.size(); i++){
                MonAn item = lsAllMonAn.get(i);
                if(item.getQuocGia() == EQuocGia.ThaiLan){

                    resultList.add(item);
                }
            }
            listDisplayMonAn.clear();      // refresh UI list
            listDisplayMonAn.addAll(resultList);
            customFoodListAdapter.notifyDataSetChanged();
            setListViewHeight(lsView);

        });
        lnHQ.setOnClickListener(view -> {
            List<MonAn> resultList = new ArrayList<>();
            for(int i=0; i<lsAllMonAn.size(); i++){
                MonAn item = lsAllMonAn.get(i);
                if(item.getQuocGia() == EQuocGia.HanQuoc){

                    resultList.add(item);
                }
            }
            listDisplayMonAn.clear();      // refresh UI list
            listDisplayMonAn.addAll(resultList);
            customFoodListAdapter.notifyDataSetChanged();
            setListViewHeight(lsView);

        });
        lnTQ.setOnClickListener(view -> {
            List<MonAn> resultList = new ArrayList<>();
            for(int i=0; i<lsAllMonAn.size(); i++){
                MonAn item = lsAllMonAn.get(i);
                if(item.getQuocGia() == EQuocGia.TrungQuoc){

                    resultList.add(item);
                }
            }
            listDisplayMonAn.clear();      // refresh UI list
            listDisplayMonAn.addAll(resultList);
            customFoodListAdapter.notifyDataSetChanged();
            setListViewHeight(lsView);

        });
        lnBestSell.setOnClickListener(view -> {
            // 1. Xóa danh sách đang hiển thị (đang bị lọc theo quốc gia khác)
            listDisplayMonAn.clear();

            // 2. Thêm tất cả món ăn từ nguồn gốc (lsAllMonAn) vào lại
            listDisplayMonAn.addAll(lsAllMonAn);

            // 3. Cập nhật giao diện
            customFoodListAdapter.notifyDataSetChanged();
            setListViewHeight(lsView);
        });





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
            Intent intent = new Intent(HomePage.this,Infor.class);
            startActivity(intent);
        });
        ibtnMenu.setOnClickListener(view -> {

            Toast.makeText(HomePage.this, "Clicked Order", Toast.LENGTH_SHORT).show();
        });

        ibtnCart.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, Cart.class);
            startActivity(intent);
        });


        lsView.setOnItemClickListener((adapterView, view, i, l) -> {
            MonAn monAnDuocChon = lsAllMonAn.get(i);
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


    @Override
    protected void onDestroy() {
        cartDbHelper.close();
        super.onDestroy();
    }
}