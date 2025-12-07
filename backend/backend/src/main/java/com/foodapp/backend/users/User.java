package com.foodapp.backend.users;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "TenDangNhap")
    private String tenDangNhap;
    @Column(name = "MatKhauHashed")
    @JsonProperty("matKhau")
    private String matKhauHashed;
    @Column(name = "role")
    private String role;
    @Column(name = "sdt")
    private String sdt;
    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "TenHienThi")
    private String tenHienThi;
    @Column(name = "GioiTinh")
    private String gioiTinh;
    @Column(name = "TrangThai")
    private String trangThai;


    public User() {
    }

    public User(Integer id, String tenDangNhap, String MatKhauHashed, String role, String sdt, String diaChi, String tenHienThi, String gioiTinh, String trangThai) {
        this.id = id;
        this.tenDangNhap = tenDangNhap;
        this.matKhauHashed = MatKhauHashed;
        this.role = role;
        this.sdt = sdt;
        this.diaChi = diaChi;

        this.tenHienThi = tenHienThi;
        this.gioiTinh = gioiTinh;
        this.trangThai = trangThai;
    }

    public User(String tenDangNhap, String MatKhauHashed, String role, String sdt, String diaChi, String tenHienThi, String gioiTinh, String trangThai) {
        this.tenDangNhap = tenDangNhap;
        this.matKhauHashed = MatKhauHashed;
        this.role = role;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.tenHienThi = tenHienThi;
        this.gioiTinh = gioiTinh;
        this.trangThai = trangThai;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhauHashed() {
        return matKhauHashed;
    }

    public void setMatKhauHashed(String matKhauHashed) {
        this.matKhauHashed = matKhauHashed;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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


    public String getTenHienThi() {
        return tenHienThi;
    }

    public void setTenHienThi(String tenHienThi) {
        this.tenHienThi = tenHienThi;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + id +
                ", TenDangNhap='" + tenDangNhap + '\'' +
                ", MatKhauHashed='" + matKhauHashed + '\'' +
                ", Role='" + role + '\'' +
                ", SDT='" + sdt + '\'' +
                ", DiaChi='" + diaChi + '\'' +
                ", TenHienThi='" + tenHienThi + '\'' +
                ", GioiTinh='" + gioiTinh + '\'' +
                ", TrangThai='" + trangThai + '\'' +
                '}';
    }
}
