package com.foodapp.backend.MonAn;

import java.math.BigDecimal;

public class MonAnDTO {
    private String TenMonAn;
    private String MoTa;
    private BigDecimal Gia;
    private String QuocGia;
    private String TrangThai;
    private String HinhAnhURL;

    public MonAnDTO() {
    }

    public MonAnDTO(String tenMonAn, String moTa, BigDecimal gia, String quocGia, String trangThai, String hinhAnhURL) {
        TenMonAn = tenMonAn;
        MoTa = moTa;
        Gia = gia;
        QuocGia = quocGia;
        TrangThai = trangThai;
        HinhAnhURL = hinhAnhURL;
    }

    public String getTenMonAn() {
        return TenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        TenMonAn = tenMonAn;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public BigDecimal getGia() {
        return Gia;
    }

    public void setGia(BigDecimal gia) {
        Gia = gia;
    }

    public String getQuocGia() {
        return QuocGia;
    }

    public void setQuocGia(String quocGia) {
        QuocGia = quocGia;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public String getHinhAnhURL() {
        return HinhAnhURL;
    }

    public void setHinhAnhURL(String hinhAnhURL) {
        HinhAnhURL = hinhAnhURL;
    }

    @Override
    public String toString() {
        return "MonAnDTO{" +
                "TenMonAn='" + TenMonAn + '\'' +
                ", MoTa='" + MoTa + '\'' +
                ", Gia=" + Gia +
                ", QuocGia='" + QuocGia + '\'' +
                ", TrangThai='" + TrangThai + '\'' +
                ", HinhAnhURL='" + HinhAnhURL + '\'' +
                '}';
    }
}
