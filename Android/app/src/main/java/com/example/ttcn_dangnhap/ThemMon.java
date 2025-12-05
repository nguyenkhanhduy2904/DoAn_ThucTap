package com.example.ttcn_dangnhap;

import android.os.Bundle;
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

import models.EQuocGia;
import models.MonAn;

public class ThemMon extends AppCompatActivity {

    private ActivityResultLauncher<String> pickImageLauncher;
    LinearLayout imgZone;
    ImageView imgPreview;
    EditText etxtFoodName, etxtFoodDesc, etxtPrice;
    RadioGroup rbgQuocGia;
    RadioButton rbVN, rbTL, rbHQ, rbTQ, rbDB, rbBT;
    Button btnCancel, btnSave;
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
                        imgPreview.setImageURI(uri); // Display selected image
                    }
                }
        );

        addControls();
        addEvents();
    }


    void addControls(){
        imgZone = findViewById(R.id.addimgZone);
        imgPreview = findViewById(R.id.imgPreview);
        etxtFoodName = findViewById(R.id.etxtFoodName);
        etxtFoodDesc = findViewById(R.id.etxtDesc);
        etxtPrice = findViewById(R.id.etxtPrice);
        rbgQuocGia = findViewById(R.id.rbgQuocGia);
        rbVN = findViewById(R.id.rbVN);
        rbTL = findViewById(R.id.rbTL);
        rbHQ = findViewById(R.id.rbHQ);
        rbTQ = findViewById(R.id.rbTQ);
        rbDB = findViewById(R.id.rbDacBiet);
        rbBT = findViewById(R.id.rbBinhThuong);

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










            MonAn monAn = new MonAn(
                    1,// can 1 method de track id cua mon an
                    etxtFoodName.getText().toString(),
                    etxtFoodDesc.getText().toString(),
                    price,
                    null,// can 1 cach de luu hinh anh?
                    quocGiaChon,
                    true

            );

            Toast.makeText(ThemMon.this, monAn.toString(), Toast.LENGTH_LONG).show();



        });

    }
}