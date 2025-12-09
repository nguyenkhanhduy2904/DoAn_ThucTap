package com.example.ttcn_dangnhap;

//<<<<<<< HEAD
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
//=======
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
//>>>>>>> e4d666c02482781d6b6512eeb65b553a58ff273c
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.Volley;
import com.example.ttcn_dangnhap.Network.APIClient;
import com.example.ttcn_dangnhap.Network.APIService;
import com.google.gson.JsonObject;

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

    private ActivityResultLauncher<String> pickImageLauncher;
    LinearLayout imgZone,layout_percent,layout_nbd,layout_nkt;
    ImageView imgPreview;
    EditText etxtFoodName, etxtFoodDesc, etxtPrice;
//<<<<<<< HEAD
//    RadioGroup rbgQuocGia;
    RadioButton rbVN, rbTL, rbHQ, rbTQ, rbConMon, rbHetMon;
//=======
    RadioGroup rbgQuocGia,rgStatus,rgDiscount;
//    RadioButton rbVN, rbTL, rbHQ, rbTQ, rb_con,rb_het;
//>>>>>>> e4d666c02482781d6b6512eeb65b553a58ff273c
    Button btnCancel, btnSave;
    TextView tv_start_date,tv_end_date;
    final Calendar calendar = Calendar.getInstance();
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
//<<<<<<< HEAD
                        imgPreview.setImageURI(uri);
                        imgPreview.setTag(uri);   // store URI for later upload
////=======
//                        img.setImageURI(uri); // Display selected image
//>>>>>>> e4d666c02482781d6b6512eeb65b553a58ff273c
                    }
                }
        );


        addControls();
        addEvents();
    }


    void addControls(){
//<<<<<<< HEAD
        imgZone = findViewById(R.id.btn_upload_image);
        imgPreview = findViewById(R.id.img);
//=======
//        btn_upload_image = findViewById(R.id.btn_upload_image);
        layout_percent = findViewById(R.id.layout_percent);
        layout_nbd = findViewById(R.id.layout_nbd);
        layout_nkt = findViewById(R.id.layout_nkt);
//        img = findViewById(R.id.img);
//>>>>>>> e4d666c02482781d6b6512eeb65b553a58ff273c
        etxtFoodName = findViewById(R.id.etxtFoodName);
        etxtFoodDesc = findViewById(R.id.etxtDesc);
        etxtPrice = findViewById(R.id.etxtPrice);
        rbgQuocGia = findViewById(R.id.rbgQuocGia);
        rgStatus = findViewById(R.id.rgStatus);
        rgDiscount = findViewById(R.id.rgDiscount);
        rbVN = findViewById(R.id.rbVN);
        rbTL = findViewById(R.id.rbTL);
        rbHQ = findViewById(R.id.rbHQ);
        rbTQ = findViewById(R.id.rbTQ);
//<<<<<<< HEAD
        rbConMon = findViewById(R.id.rb_con);
        rbHetMon = findViewById(R.id.rb_het);
//        rbDB = findViewById(R.id.rbDacBiet);
//        rbBT = findViewById(R.id.rbBinhThuong);

//=======
        tv_start_date=findViewById(R.id.tv_start_date);
        tv_end_date=findViewById(R.id.tv_end_date);
        tv_start_date.setText(Format_Date.formatDate(calendar.getTime()));
        tv_end_date.setText(Format_Date.formatDate(calendar.getTime()));
//>>>>>>> e4d666c02482781d6b6512eeb65b553a58ff273c
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
    }

    void addEvents() {
        imgZone.setOnClickListener(view -> {
            pickImageLauncher.launch("image/*");  // Opens gallery
        });

        btnCancel.setOnClickListener(view -> {
            finish();
        });
        tv_start_date.setOnClickListener(view -> XulyChonNgay(view, tv_start_date));
        tv_end_date.setOnClickListener(view -> XulyChonNgay(view, tv_end_date));
        if (rgDiscount.getCheckedRadioButtonId() == R.id.rdo_off) {
            hienthi(false);
        }
        rgDiscount.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdo_on) {
                    hienthi(true);
                } else if (checkedId == R.id.rdo_off) {
                    hienthi(false);
                }
            }
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

//<<<<<<< HEAD
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



            uploadImage(selectedImgUri);








//
//=======
//            String urlUploadImg ="http://10.0.2.2:8080/api/v1/upload/image";
//>>>>>>> e4d666c02482781d6b6512eeb65b553a58ff273c
        });
    }
//<<<<<<< HEAD

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


//=======
    public void XulyChonNgay(View v, TextView tv) {
        DatePickerDialog dlg = new DatePickerDialog(ThemMon.this,
                (datePicker, i, i1, i2) -> {
                    calendar.set(Calendar.YEAR, i);
                    calendar.set(Calendar.MONTH, i1);
                    calendar.set(Calendar.DATE, i2);
                    tv.setText(Format_Date.formatDate(calendar.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        );
        dlg.show();
    }
    private void hienthi(boolean isVisible) {
        int visibility = isVisible ? View.VISIBLE : View.GONE;
        layout_percent.setVisibility(visibility);
        layout_nbd.setVisibility(visibility);
        layout_nkt.setVisibility(visibility);
    }
//>>>>>>> e4d666c02482781d6b6512eeb65b553a58ff273c
}