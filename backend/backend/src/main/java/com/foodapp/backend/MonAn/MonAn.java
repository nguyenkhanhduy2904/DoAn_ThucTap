package com.foodapp.backend.MonAn;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
@Table(name = "monan")
public class MonAn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "TenMonAn")
    private String tenMonAn;
    @Column(name = "MoTa")
    private String moTa;
    @Column(name = "Gia")
    private BigDecimal gia;
    @Column(name = "QuocGia")
    private String quocGia;
    @Column(name = "TrangThai")
    private String trangThai;
    @Column(name = "HinhAnhURL")
    private String hinhAnhURL;

    public MonAn() {
    }

    public MonAn(Integer id, String tenMonAn, String moTa, BigDecimal gia, String quocGia, String trangThai, String hinhAnhURL) {
        this.id = id;
        this.tenMonAn = tenMonAn;
        this.moTa = moTa;
        this.gia = gia;
        this.quocGia = quocGia;
        this.trangThai = trangThai;
        this.hinhAnhURL = hinhAnhURL;
    }

    public MonAn(String tenMonAn, String moTa, BigDecimal gia, String quocGia, String trangThai, String hinhAnhURL) {
        this.tenMonAn = tenMonAn;
        this.moTa = moTa;
        this.gia = gia;
        this.quocGia = quocGia;
        this.trangThai = trangThai;
        this.hinhAnhURL = hinhAnhURL;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public BigDecimal getGia() {
        return gia;
    }

    public void setGia(BigDecimal gia) {
        this.gia = gia;
    }

    public String getQuocGia() {
        return quocGia;
    }

    public void setQuocGia(String quocGia) {
        this.quocGia = quocGia;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getHinhAnhURL() {
        return hinhAnhURL;
    }

    public void setHinhAnhURL(String hinhAnhURL) {
        this.hinhAnhURL = hinhAnhURL;
    }

    @Override
    public String toString() {
        return "MonAn{" +
                "ID=" + id +
                ", TenMonAn='" + tenMonAn + '\'' +
                ", MoTa='" + moTa + '\'' +
                ", Gia=" + gia +
                ", QuocGia='" + quocGia + '\'' +
                ", TrangThai='" + trangThai + '\'' +
                ", HinhAnhURL='" + hinhAnhURL + '\'' +
                '}';
    }
}
