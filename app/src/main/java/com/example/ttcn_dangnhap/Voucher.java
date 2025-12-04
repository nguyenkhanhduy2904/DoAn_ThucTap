package com.example.ttcn_dangnhap;

public class Voucher {
    private String tenVoucher;
    private String moTa;
    private String loaiUuDai;
    private String ngay;
    private String anhUri;

    public Voucher(String tenVoucher, String moTa, String loaiUuDai, String ngay, String anhUri) {
        this.tenVoucher = tenVoucher;
        this.moTa = moTa;
        this.loaiUuDai = loaiUuDai;
        this.ngay = ngay;
        this.anhUri = anhUri;
    }

    public String getTenVoucher() {
        return tenVoucher;
    }

    public void setTenVoucher(String tenVoucher) {
        this.tenVoucher = tenVoucher;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getLoaiUuDai() {
        return loaiUuDai;
    }

    public void setLoaiUuDai(String loaiUuDai) {
        this.loaiUuDai = loaiUuDai;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay= ngay;
    }

    public String getAnhUri() {
        return anhUri;
    }

    public void setAnhUri(String anhUri) {
        this.anhUri = anhUri;
    }
}
