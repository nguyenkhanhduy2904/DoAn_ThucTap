package com.foodapp.backend.users;

public class UserDTO {
    private String tenHienThi;
    private String sdt;
    private String diaChi;
    private String gioiTinh;

    public UserDTO() {
    }

    public UserDTO(String tenHienThi, String sdt, String diaChi, String gioiTinh) {
        this.tenHienThi = tenHienThi;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
    }

    public String getTenHienThi() {
        return tenHienThi;
    }

    public void setTenHienThi(String tenHienThi) {
        this.tenHienThi = tenHienThi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "TenHienThi='" + tenHienThi + '\'' +
                ", SDT='" + sdt + '\'' +
                ", DiaChi='" + diaChi + '\'' +
                ", GioiTinh='" + gioiTinh + '\'' +
                '}';
    }
}
