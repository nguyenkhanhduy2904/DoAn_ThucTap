package com.foodapp.backend.users;

public class UserDTO {
    private Integer id;
    private String tenHienThi;
    private String sdt;
    private String role;
    private String diaChi;
    private String gioiTinh;
    private String trangThai;
    private String email;

    public UserDTO() {
    }

    public UserDTO(Integer id,String tenHienThi, String sdt, String role, String diaChi, String gioiTinh, String trangThai, String email) {
        this.id = id;
        this.tenHienThi = tenHienThi;
        this.sdt = sdt;
        this.role = role;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.trangThai = trangThai;
        this.email = email;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
