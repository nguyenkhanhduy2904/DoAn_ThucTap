package com.foodapp.backend.login;

public class LoginRequest {
    private String tenDangNhap;
    private String matKhauRaw;


    public LoginRequest() {
    }

    public LoginRequest(String matKhauRaw, String tenDangNhap) {
        this.matKhauRaw = matKhauRaw;
        this.tenDangNhap = tenDangNhap;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhauRaw() {
        return matKhauRaw;
    }

    public void setMatKhauRaw(String matKhauRaw) {
        this.matKhauRaw = matKhauRaw;
    }


}
