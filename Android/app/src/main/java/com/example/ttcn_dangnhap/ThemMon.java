package com.example.ttcn_dangnhap;

//<<<<<<< HEAD
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
//=======
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
//>>>>>>> e4d666c02482781d6b6512eeb65b553a58ff273c
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//<<<<<<< HEAD
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ttcn_dangnhap.Network.APIClient;
import com.example.ttcn_dangnhap.Network.APIService;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import models.APIResponse;
//=======
import java.util.Calendar;

//>>>>>>> e4d666c02482781d6b6512eeb65b553a58ff273c
import models.EQuocGia;
import models.ImageUpload;
import models.MonAn;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ThemMon extends AppCompatActivity {

    boolean isUpdate = false;
    boolean isImageChanged = false;
    MonAn monAn = null;


    private ActivityResultLauncher<String> pickImageLauncher;
    LinearLayout imgZone;
    ImageView imgPreview;
    ImageButton ibtnBack;
    EditText etxtFoodName, etxtFoodDesc, etxtPrice;
    RadioButton rbVN, rbTL, rbHQ, rbTQ, rbConMon, rbHetMon;
    RadioGroup rbgQuocGia,rgStatus,rgDiscount;
    Button  btnSave;
    private EQuocGia quocGiaChon = null;
    private String imgUrl = null;
    private String TenMonAn, MoTa, TrangThai;
    private long price =0;
    private Uri selectedImgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_them_mon);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {

                        //check file size
                        long fileSize = getFileSize(uri);

                        long maxSize = 10 * 1024 * 1024; // 10MB limit

                        if (fileSize > maxSize) {
                            Toast.makeText(this, "Hình quá lớn! Tối đa 10MB.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(isUpdate){
                            isImageChanged =true;
                        }

                        imgPreview.setImageURI(uri);
                        imgPreview.setTag(uri);
                    }
                }
        );
        addControls();
        addEvents();
        GetIntentData();
    }


    void addControls(){

        imgZone = findViewById(R.id.btn_upload_image);
        imgPreview = findViewById(R.id.img);
        ibtnBack= findViewById(R.id.ibtnBack);
        etxtFoodName = findViewById(R.id.etxtFoodName);
        etxtFoodDesc = findViewById(R.id.etxtDesc);
        etxtPrice = findViewById(R.id.etxtPrice);
        rbgQuocGia = findViewById(R.id.rbgQuocGia);
        rgStatus = findViewById(R.id.rgStatus);
        rbVN = findViewById(R.id.rbVN);
        rbTL = findViewById(R.id.rbTL);
        rbHQ = findViewById(R.id.rbHQ);
        rbTQ = findViewById(R.id.rbTQ);
        rbConMon = findViewById(R.id.rb_con);
        rbHetMon = findViewById(R.id.rb_het);
        btnSave = findViewById(R.id.btnSave);
    }

    void addEvents() {
        imgZone.setOnClickListener(view -> {
            pickImageLauncher.launch("image/*");  // Opens gallery
        });
        ibtnBack.setOnClickListener(view -> {
            Intent intent = new Intent(this, QuanLyMon.class);
            startActivity(intent);
        });
        btnSave.setOnClickListener(view -> {
            String priceText = etxtPrice.getText().toString().trim();

            TenMonAn = etxtFoodName.getText().toString().trim();
            MoTa = etxtFoodDesc.getText().toString().trim();

            if(TenMonAn.isBlank()){
                Toast.makeText(this, "Hãy nhập tên món ăn", Toast.LENGTH_SHORT).show();
                return;
            }
            if(MoTa.isBlank()){
                Toast.makeText(this, "Hãy nhập mô tả", Toast.LENGTH_SHORT).show();
                return;
            }

            if (priceText.isEmpty()) {
                price = 0;
            } else {
                price = Long.parseLong(priceText);
            }

            if (rbVN.isChecked()) {
                quocGiaChon = EQuocGia.VietNam;
            } else if (rbTL.isChecked()) {
                quocGiaChon = EQuocGia.ThaiLan;
            } else if (rbHQ.isChecked()) {
                quocGiaChon = EQuocGia.HanQuoc;
            } else if (rbTQ.isChecked()) {
                quocGiaChon = EQuocGia.TrungQuoc;
            } else {
                Toast.makeText(this, "Hãy chọn quốc gia!", Toast.LENGTH_SHORT).show();
                return;
            }

            if(rbConMon.isChecked()){
                TrangThai = "Available";
            } else if (rbHetMon.isChecked()) {
                TrangThai =" Out of Stock";
            }


            //upload image
            selectedImgUri = (Uri) imgPreview.getTag();
            if(selectedImgUri == null && !isUpdate){
                Toast.makeText(this, "Hãy chọn hình ảnh!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isUpdate && !isImageChanged) {
                sendMonAn(true);  // update without changing image
            } else {
                uploadImage(selectedImgUri, () -> {
                    if (isUpdate) {
                        if(monAn.getUrlHinhAnhMonAn()!=null){

                            deleteOldImg(monAn.getUrlHinhAnhMonAn());

                        }
                        sendMonAn(true);  // update after image upload
                    } else {
                        sendMonAn(false); // create after image upload
                    }
                });
            }


        });
    }

    private void deleteOldImg(String imageUrl) {
        try {
            if (imageUrl == null || imageUrl.isEmpty()) return;

            // Extract filename from URL
            String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

            // Correct backend URL and query param
            String url = "http://10.0.2.2:8080/api/v1/upload/image?fileName=" + filename + "&type=food";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null,
                    response -> Log.d("ThemMon", "Old image deleted (or attempted)"),
                    error -> Log.d("ThemMon", "Failed to delete old image: " + error.getMessage()));

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private JSONObject buildMonAnJson(){
        JSONObject jsonBody = new JSONObject();
        try{
            if (isUpdate) {
                jsonBody.put("id", monAn.getIdMonAn()); // you need to store current MonAn ID
            }
            jsonBody.put("tenMonAn", TenMonAn);
            jsonBody.put("moTa", MoTa);
            jsonBody.put("gia", price);
            jsonBody.put("quocGia", quocGiaChon.name());
            jsonBody.put("trangThai", TrangThai);
            // add imgUrl only if it exists
            if(imgUrl != null && !imgUrl.isEmpty()){
                jsonBody.put("hinhAnhURL", imgUrl);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  jsonBody;

    }

    private void uploadImage(Uri uri, Runnable onSuccess){
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            byte[] bytes = toBytes(inputStream);

            RequestBody requestFile = RequestBody.create(
                    okhttp3.MediaType.parse("image/*"),
                    bytes
            );

            MultipartBody.Part filePart = MultipartBody.Part.createFormData(
                    "file",
                    "upload.png",
                    requestFile
            );

            RequestBody typePart = RequestBody.create(
                    okhttp3.MediaType.parse("text/plain"),
                    "food"
            );

            APIService api = APIClient.getClient().create(APIService.class);

            Call<APIResponse<ImageUpload>> call = api.uploadImage(filePart, typePart);

            call.enqueue(new retrofit2.Callback<APIResponse<ImageUpload>>() {
                @Override
                public void onResponse(Call<APIResponse<ImageUpload>> call,
                                       retrofit2.Response<APIResponse<ImageUpload>> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        ImageUpload image = response.body().getData();

                        if (image != null && image.getUrl() != null) {
                            imgUrl = image.getUrl();  // store URL globally
                            Toast.makeText(ThemMon.this,
                                    "Upload thành công: " + imgUrl,
                                    Toast.LENGTH_SHORT).show();

                            if (onSuccess != null) {
                                onSuccess.run();  // continue next step
                            }
                        }
                    } else {
                        Toast.makeText(ThemMon.this,
                                "Upload failed!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<APIResponse<ImageUpload>> call, Throwable t) {
                    Toast.makeText(ThemMon.this,
                            "Error: " + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void sendMonAn(boolean isUpdate) {
        String url = "http://10.0.2.2:8080/api/v1/monan" + (isUpdate ? "/" + monAn.getIdMonAn() : "");
        int method = isUpdate ? Request.Method.PUT : Request.Method.POST;

        try {
            JSONObject jsonBody = buildMonAnJson();
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest request = new JsonObjectRequest(method, url, jsonBody,
                    response -> {
                        Toast.makeText(this, isUpdate ? "Update success" : "Create success", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(this, QuanLyMon.class);
                                    startActivity(intent);
                                    finish();
                    },
                    error -> {
                        Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    });
            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private byte[] toBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[4096];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }

    private long getFileSize(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        long size = -1;

        if (cursor != null) {
            int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
            cursor.moveToFirst();
            size = cursor.getLong(sizeIndex);
            cursor.close();
        }

        return size; // size in bytes
    }
    void GetIntentData(){
        monAn = (MonAn) getIntent().getSerializableExtra("monAn");
        if(monAn!=null){
            isUpdate = true;

            etxtFoodName.setText(monAn.getTenMonAn());
            etxtFoodDesc.setText(monAn.getMotaMonAn());
            etxtPrice.setText(String.valueOf(monAn.getGiaMonAn()));
            Picasso.get().load(monAn.getUrlHinhAnhMonAn()).resize(150,150).centerCrop().into(imgPreview);

            EQuocGia qgMonan = monAn.getQuocGia();
            switch (qgMonan){
                case VietNam:
                    rbVN.setChecked(true);
                    break;
                case ThaiLan:
                    rbTL.setChecked(true);
                    break;
                case HanQuoc:
                    rbHQ.setChecked(true);
                    break;
                case TrungQuoc:
                    rbTQ.setChecked(true);
                    break;
            }

            boolean trangThai = monAn.isTrangThai();

            if(trangThai){
                rbConMon.setChecked(true);
            }
            else{
                rbHetMon.setChecked(true);
            }
        }
    }

}