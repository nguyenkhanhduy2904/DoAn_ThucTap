package com.example.ttcn_dangnhap;

//import static com.example.ttcn_dangnhap.Infor.PREFS_NAME;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.ttcn_dangnhap.Adapter.CustomFoodListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import models.EQuocGia;
import models.MonAn;

public class QuanLyMon extends AppCompatActivity {
//<<<<<<< HEAD
    ImageButton ibtnLogout;
    Button btn_them_mon;
//=======
//    Button btn_them_mon,btnLogout;
//>>>>>>> 565edf5e6b13b3821d52b44f6d0c273890daba86
    LinearLayout layoutVietNam, layoutThaiLand, layoutSKorea, layoutChina, layoutBestSell;
    ListView lv_danh_sach_mon_an;
    List<MonAn> lsAllMonAn, listDisplayMonAn;
    CustomFoodListAdapter customFoodListAdapter;
//<<<<<<< HEAD

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

//=======
//    private static final String PREFS_NAME = "UserPrefs";
//    private static final String KEY_IS_LOGGED_IN = "is_logged_in"
//>>>>>>> 565edf5e6b13b3821d52b44f6d0c273890daba86
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_mon);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lsAllMonAn = new ArrayList<>();
        listDisplayMonAn = new ArrayList<>();
        addControls();
        addEvents();


    }

    @Override
    protected void onResume() {
        super.onResume();
        GETAllItem(new APICallback<List<MonAn>>() {
            @Override
            public void onSuccess(List<MonAn> result) {
                lsAllMonAn.clear();            // refresh data source
                lsAllMonAn.addAll(result);     // fill all items

                listDisplayMonAn.clear();      // refresh UI list
                listDisplayMonAn.addAll(lsAllMonAn);

                customFoodListAdapter.notifyDataSetChanged();
                setListViewHeight(lv_danh_sach_mon_an);

                Log.d("HomePage", "List size: " + listDisplayMonAn.size());


            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(QuanLyMon.this, errorMessage, Toast.LENGTH_LONG).show();

            }
        });
    }

    private void addControls() {
        btn_them_mon = findViewById(R.id.btn_them_mon);
//        btnLogout=findViewById(R.id.btnLogout);
        layoutBestSell = findViewById(R.id.layoutBestSell);
        layoutVietNam = findViewById(R.id.layoutVietNam);
        layoutThaiLand = findViewById(R.id.layoutThaiLand);
        layoutSKorea = findViewById(R.id.layoutSKorea);
        layoutChina = findViewById(R.id.layoutChina);
        lv_danh_sach_mon_an = findViewById(R.id.lv_danh_sach_mon_an);
        customFoodListAdapter = new CustomFoodListAdapter(this, listDisplayMonAn, null, true);
        lv_danh_sach_mon_an.setAdapter(customFoodListAdapter);
        ibtnLogout = findViewById(R.id.ibtn_logout);
    }

    private void addEvents() {
        btn_them_mon.setOnClickListener(view -> {
            Intent intent = new Intent(QuanLyMon.this,ThemMon.class);
            startActivity(intent);
        });
        lv_danh_sach_mon_an.setOnItemClickListener((adapterView, view, i, l) -> {
            MonAn monAnDuocChon = lsAllMonAn.get(i);
            Intent intent = new Intent(QuanLyMon.this, ThemMon.class);
            intent.putExtra("monAn", monAnDuocChon);
            startActivity(intent);
        });
        layoutVietNam.setOnClickListener(view -> {
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
            setListViewHeight(lv_danh_sach_mon_an);

        });
        layoutThaiLand.setOnClickListener(view -> {
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
            setListViewHeight(lv_danh_sach_mon_an);

        });
        layoutSKorea.setOnClickListener(view -> {
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
            setListViewHeight(lv_danh_sach_mon_an);

        });
//        btnLogout.setOnClickListener(view -> {
//            SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//            SharedPreferences.Editor editor = settings.edit();
//            editor.putBoolean(KEY_IS_LOGGED_IN, false);
//            editor.remove("userid");
//            editor.apply();
//
//            Intent intent = new Intent(QuanLyMon.this, Login.class);
//            startActivity(intent);
//            finish();
//
//            Toast.makeText(Infor.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
//
//        });
        layoutChina.setOnClickListener(view -> {
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
            setListViewHeight(lv_danh_sach_mon_an);

        });
        layoutBestSell.setOnClickListener(view -> {
            // 1. Xóa danh sách đang hiển thị (đang bị lọc theo quốc gia khác)
            listDisplayMonAn.clear();

            // 2. Thêm tất cả món ăn từ nguồn gốc (lsAllMonAn) vào lại
            listDisplayMonAn.addAll(lsAllMonAn);

            // 3. Cập nhật giao diện
            customFoodListAdapter.notifyDataSetChanged();
            setListViewHeight(lv_danh_sach_mon_an);
        });

        ibtnLogout.setOnClickListener(view -> {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(KEY_IS_LOGGED_IN, false);
            editor.remove("userid");
            editor.apply();

            Intent intent = new Intent(QuanLyMon.this, Login.class);
            startActivity(intent);
            finish();

            Toast.makeText(QuanLyMon.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
//            checkLoginState();
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