package com.example.ttcn_dangnhap;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
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

import java.util.Calendar;

import models.EQuocGia;
import models.MonAn;

public class ThemMon extends AppCompatActivity {

    private ActivityResultLauncher<String> pickImageLauncher;
    LinearLayout btn_upload_image,layout_percent,layout_nbd,layout_nkt;
    ImageView img;
    EditText etxtFoodName, etxtFoodDesc, etxtPrice;
    RadioGroup rbgQuocGia,rgStatus,rgDiscount;
    RadioButton rbVN, rbTL, rbHQ, rbTQ, rb_con,rb_het;
    Button btnCancel, btnSave;
    TextView tv_start_date,tv_end_date;
    final Calendar calendar = Calendar.getInstance();
    private EQuocGia quocGiaChon = null;




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
                        img.setImageURI(uri); // Display selected image
                    }
                }
        );

        addControls();
        addEvents();
    }


    void addControls(){
        btn_upload_image = findViewById(R.id.btn_upload_image);
        layout_percent = findViewById(R.id.layout_percent);
        layout_nbd = findViewById(R.id.layout_nbd);
        layout_nkt = findViewById(R.id.layout_nkt);
        img = findViewById(R.id.img);
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
        tv_start_date=findViewById(R.id.tv_start_date);
        tv_end_date=findViewById(R.id.tv_end_date);
        tv_start_date.setText(Format_Date.formatDate(calendar.getTime()));
        tv_end_date.setText(Format_Date.formatDate(calendar.getTime()));
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
    }

    void addEvents() {
        img.setOnClickListener(view -> {
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

            long price;
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

            String urlUploadImg ="http://10.0.2.2:8080/api/v1/upload/image";
        });

    }
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
}