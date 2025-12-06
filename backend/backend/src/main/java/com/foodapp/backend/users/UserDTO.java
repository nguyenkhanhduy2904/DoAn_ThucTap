package com.foodapp.backend.users;

public class UserDTO {
    private String TenHienThi;
    private String SDT;
    private String DiaChi;
    private String GioiTinh;

    public UserDTO() {
    }

    public UserDTO(String tenHienThi, String SDT, String diaChi, String gioiTinh) {
        TenHienThi = tenHienThi;
        this.SDT = SDT;
        DiaChi = diaChi;
        GioiTinh = gioiTinh;
    }

    public String getTenHienThi() {
        return TenHienThi;
    }

    public void setTenHienThi(String tenHienThi) {
        TenHienThi = tenHienThi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "TenHienThi='" + TenHienThi + '\'' +
                ", SDT='" + SDT + '\'' +
                ", DiaChi='" + DiaChi + '\'' +
                ", GioiTinh='" + GioiTinh + '\'' +
                '}';
    }
}
