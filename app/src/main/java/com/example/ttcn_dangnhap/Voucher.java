package com.example.ttcn_dangnhap;

public class Voucher {
    private String tenVoucher;
    private String moTa;
    private String loaiUuDai;
    private String ngaybd;
    private String ngaykt;
    private String anhUri;

    public Voucher(String tenVoucher, String moTa, String loaiUuDai, String ngaybd, String ngaykt, String anhUri) {
        this.tenVoucher = tenVoucher;
        this.moTa = moTa;
        this.loaiUuDai = loaiUuDai;
        this.ngaybd = ngaybd;
        this.ngaykt=ngaykt;
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

    public String getNgaybd() {
        return ngaybd;
    }

    public void setNgayBd(String ngaybd) {
        this.ngaybd= ngaybd;
    }
    public String getNgaykt() {
        return ngaykt;
    }

    public void setNgaykt(String ngaykt) {
        this.ngaykt = ngaykt;
    }

    public String getAnhUri() {
        return anhUri;
    }

    public void setAnhUri(String anhUri) {
        this.anhUri = anhUri;
    }
}
