package com.example.ttcn_dangnhap;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Them_Voucher extends AppCompatActivity {
    ImageView btn_back;
    LinearLayout btn_upload_image;
    ImageView img;
    EditText txt_ten_ct,txt_mota_ct;
    RadioGroup rdg_loai_uu_dai;
    RadioButton rdo_giam_phan_tram,rdo_giam_tien;
    List<String> listDieuKienPhanTram;
    List<String> listDieuKienTienMat;
    Spinner spinner_dieu_kien;
    TextView tv_start_date,tv_end_date;
    Button btn_tao_voucher;
    final Calendar calendar = Calendar.getInstance();
    Uri anh = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_them_voucher);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        setupData();
        addEvents();
    }

    private void addControls() {
        btn_back=findViewById(R.id.btn_back);
        btn_upload_image=findViewById(R.id.btn_upload_image);
        img=findViewById(R.id.img);
        txt_ten_ct=findViewById(R.id.txt_ten_ct);
        txt_mota_ct=findViewById(R.id.txt_mota_ct);
        rdg_loai_uu_dai=findViewById(R.id.rdg_loai_uu_dai);
        rdo_giam_phan_tram=findViewById(R.id.rdo_giam_phan_tram);
        rdo_giam_tien=findViewById(R.id.rdo_giam_tien);
        spinner_dieu_kien=findViewById(R.id.spinner_dieu_kien);
        tv_start_date=findViewById(R.id.tv_start_date);
        tv_end_date=findViewById(R.id.tv_end_date);
        tv_start_date.setText(Format_Date.formatDate(calendar.getTime()));
        tv_end_date.setText(Format_Date.formatDate(calendar.getTime()));
        btn_tao_voucher=findViewById(R.id.btn_tao_voucher);
    }

    private void addEvents() {
        btn_back.setOnClickListener(view -> finish());
        btn_upload_image.setOnClickListener(view -> {
            Mo_Thuvien();
        });
        img.setOnClickListener(view -> {
            Mo_Thuvien();
        });
        rdg_loai_uu_dai.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId== R.id.rdo_giam_phan_tram){
                loadSpinnerData(listDieuKienPhanTram);
                }
            else if(checkedId==R.id.rdo_giam_tien){
                loadSpinnerData(listDieuKienTienMat);
                }
        });
        tv_start_date.setOnClickListener(view -> XulyChonNgay(view,tv_start_date));
        tv_end_date.setOnClickListener(view -> XulyChonNgay(view,tv_end_date));
        btn_tao_voucher.setOnClickListener(view -> {
            String tenVoucher = txt_ten_ct.getText().toString().trim();
            String moTa = txt_mota_ct.getText().toString().trim();

            if (tenVoucher.isEmpty() || moTa.isEmpty()) {
                ThongBao.showThongBao(Them_Voucher.this, "Thông báo", "Vui lòng nhập đầy đủ thông tin!",null);
                return;
            }
            String ngayBatDau = tv_start_date.getText().toString();
            String ngayKetThuc = tv_end_date.getText().toString();
            String loaiUuDai = "Giảm tiền mặt";
            if (rdo_giam_phan_tram.isChecked()) {
                loaiUuDai = "Giảm phần trăm";
            }
            String dieuKien = "";
            if (spinner_dieu_kien.getSelectedItem() != null) {
                dieuKien = spinner_dieu_kien.getSelectedItem().toString();
            }
            Intent intent = new Intent(Them_Voucher.this, Voucher_admin.class);
            intent.putExtra("TEN", tenVoucher);
            intent.putExtra("MO_TA", moTa);
            intent.putExtra("NGAY_BD", ngayBatDau);
            intent.putExtra("NGAY_KT", ngayKetThuc);
            intent.putExtra("LOAI", loaiUuDai);
            intent.putExtra("DIEU_KIEN", dieuKien);

            // Gửi ảnh (Chuyển Uri thành String)
            if (anh != null) {
                intent.putExtra("ANH", anh.toString());
            }

            startActivity(intent);
             finish();
        });
    }
    private void loadSpinnerData(List<String> dataList) {
        if (dataList == null) return;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                dataList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_dieu_kien.setAdapter(adapter);
    }
    private void Mo_Thuvien(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null)
        {
            anh = data.getData();
            img.setImageURI(data.getData());
            img.setVisibility(View.VISIBLE);
            btn_upload_image.setVisibility(View.GONE);
        }
    }
    public void XulyChonNgay(View v,TextView tv) {
        DatePickerDialog dlg = new DatePickerDialog(Them_Voucher.this,
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
    private void setupData() {
        listDieuKienPhanTram = new ArrayList<>();
        listDieuKienPhanTram.add("Tối đa 50.000đ");
        listDieuKienPhanTram.add("Tối đa 100.000đ");

        listDieuKienTienMat = new ArrayList<>();
        listDieuKienTienMat.add("Cho đơn từ 0đ");
        listDieuKienTienMat.add("Cho đơn từ 100.000đ");
        listDieuKienTienMat.add("Cho đơn từ 200.000đ");
        listDieuKienTienMat.add("Cho đơn từ 500.000đ");
    }
}