package com.example.ttcn_dangnhap;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ttcn_dangnhap.Network.APIClient;
import com.example.ttcn_dangnhap.Network.APIService;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import models.APIResponse;
import models.EQuocGia;
import models.ImageUpload;
import models.MonAn;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ThemMon extends AppCompatActivity {

    private ActivityResultLauncher<String> pickImageLauncher;
    LinearLayout imgZone;
    ImageView imgPreview;
    EditText etxtFoodName, etxtFoodDesc, etxtPrice;
    RadioGroup rbgQuocGia;
    RadioButton rbVN, rbTL, rbHQ, rbTQ, rbConMon, rbHetMon;
    Button btnCancel, btnSave;
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
                        imgPreview.setImageURI(uri);
                        imgPreview.setTag(uri);   // store URI for later upload
                    }
                }
        );


        addControls();
        addEvents();
    }


    void addControls(){
        imgZone = findViewById(R.id.btn_upload_image);
        imgPreview = findViewById(R.id.img);
        etxtFoodName = findViewById(R.id.etxtFoodName);
        etxtFoodDesc = findViewById(R.id.etxtDesc);
        etxtPrice = findViewById(R.id.etxtPrice);
        rbgQuocGia = findViewById(R.id.rbgQuocGia);
        rbVN = findViewById(R.id.rbVN);
        rbTL = findViewById(R.id.rbTL);
        rbHQ = findViewById(R.id.rbHQ);
        rbTQ = findViewById(R.id.rbTQ);
        rbConMon = findViewById(R.id.rbAvailable);
        rbHetMon = findViewById(R.id.rbOutOfStock);
//        rbDB = findViewById(R.id.rbDacBiet);
//        rbBT = findViewById(R.id.rbBinhThuong);

        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
    }

    void addEvents(){
        imgZone.setOnClickListener(view -> {
            pickImageLauncher.launch("image/*");  // Opens gallery
        });

        btnCancel.setOnClickListener(view -> {
            finish();
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
                quocGiaChon = EQuocGia.VietNam;  // If you add this enum later
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
            String urlUploadImg ="http://10.0.2.2:8080/api/v1/upload/image";

            selectedImgUri = (Uri) imgPreview.getTag();
            if(selectedImgUri == null){
                Toast.makeText(this, "Hãy chọn hình ảnh!", Toast.LENGTH_SHORT).show();
                return;
            }


//            String TenMonAn= etxtFoodName.getText().toString().trim();
//            String MoTa = etxtFoodDesc.getText().toString().trim();
//            String QuocGia = quocGiaChon.name();

//            if(TenMonAn.isBlank()){
//                Toast.makeText(ThemMon.this, "Vui lòng Nhập tên món ăn",Toast.LENGTH_LONG).show();
//                return;
//            }


            uploadImage(selectedImgUri);









        });
    }

    private void BuildMonAnJsonAndSend(){

        String urlAddMon = "http://10.0.2.2:8080/api/v1/monan";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("tenMonAn", TenMonAn);
            jsonBody.put("moTa", MoTa);
            jsonBody.put("gia", price);
            jsonBody.put("hinhAnhURL", imgUrl);
            jsonBody.put("quocGia", quocGiaChon.name());
            jsonBody.put("trangThai", TrangThai);
        }
        catch (Exception e){
            Toast.makeText(ThemMon.this, e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                urlAddMon,
                jsonBody,
                response -> {
                    try {
                        String status = response.getString("status");
                        String message = response.getString("message");
                        if(status.equals("success")){
                            Toast.makeText(ThemMon.this, "Create New Food Success", Toast.LENGTH_LONG).show();
                            //intent chuyen activity

                        }else{
                            Toast.makeText(ThemMon.this, "Create Food failed:"+ message, Toast.LENGTH_LONG).show();
                        }

                    }catch (Exception e){
                        Toast.makeText(ThemMon.this, "Error convert request: " + e.getMessage(),Toast.LENGTH_LONG ).show();
                    }
                }, error -> {
            if (error.networkResponse != null && error.networkResponse.data != null) {
                try {
                    String errorJson = new String(error.networkResponse.data, "UTF-8");
                    JSONObject obj = new JSONObject(errorJson);

                    String status = obj.getString("status");
                    String message = obj.getString("message");

                    Toast.makeText(ThemMon.this, message, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(ThemMon.this, "Error parsing error response" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(ThemMon.this, "Connection Error", Toast.LENGTH_LONG).show();
            }
        }
        );

        queue.add(request);
    }

    private void uploadImage(Uri uri) {
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

                    Log.e("UPLOAD_DEBUG", "Response code: " + response.code());
                    Log.e("UPLOAD_DEBUG", "Raw: " + response.raw().toString());

                    if (!response.isSuccessful()) {
                        try {
                            Log.e("UPLOAD_DEBUG", "Error body: " + response.errorBody().string());
                        } catch (Exception e) {
                            Log.e("UPLOAD_DEBUG", "Error parsing error body: " + e.getMessage());
                        }
                    }

                    if (response.isSuccessful() && response.body() != null) {
                        ImageUpload image = response.body().getData();

                        if (image != null && image.getUrl() != null) {
                            Log.d("UPLOAD", "Image URL: " + image.getUrl());
                            Toast.makeText(ThemMon.this,
                                    "Upload thành công: " + image.getUrl(),
                                    Toast.LENGTH_LONG).show();
                            imgUrl = image.getUrl();

                            BuildMonAnJsonAndSend();


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

    private byte[] toBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[4096];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }


}