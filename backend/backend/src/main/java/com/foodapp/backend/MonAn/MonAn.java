package com.foodapp.backend.MonAn;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
@Table(name = "monan")
public class MonAn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;
    @Column(name = "TenMonAn")
    private String TenMonAn;
    @Column(name = "MoTa")
    private String MoTa;
    @Column(name = "Gia")
    private BigDecimal Gia;
    @Column(name = "QuocGia")
    private String QuocGia;
    @Column(name = "TrangThai")
    private String TrangThai;
    @Column(name = "HinhAnhURL")
    private String HinhAnhURL;

    public MonAn() {
    }

    public MonAn(Integer ID, String tenMonAn, String moTa, BigDecimal gia, String quocGia, String trangThai, String hinhAnhURL) {
        this.ID = ID;
        TenMonAn = tenMonAn;
        MoTa = moTa;
        Gia = gia;
        QuocGia = quocGia;
        TrangThai = trangThai;
        HinhAnhURL = hinhAnhURL;
    }

    public MonAn(String tenMonAn, String moTa, BigDecimal gia, String quocGia, String trangThai, String hinhAnhURL) {
        TenMonAn = tenMonAn;
        MoTa = moTa;
        Gia = gia;
        QuocGia = quocGia;
        TrangThai = trangThai;
        HinhAnhURL = hinhAnhURL;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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
        return "MonAn{" +
                "ID=" + ID +
                ", TenMonAn='" + TenMonAn + '\'' +
                ", MoTa='" + MoTa + '\'' +
                ", Gia=" + Gia +
                ", QuocGia='" + QuocGia + '\'' +
                ", TrangThai='" + TrangThai + '\'' +
                ", HinhAnhURL='" + HinhAnhURL + '\'' +
                '}';
    }
}
