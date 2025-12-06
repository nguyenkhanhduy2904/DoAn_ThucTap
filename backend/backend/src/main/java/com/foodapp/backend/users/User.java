package com.foodapp.backend.users;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;
    @Column(name = "TenDangNhap")
    private String TenDangNhap;
    @Column(name = "MatKhauHashed")
    @JsonProperty("MatKhau")
    private String MatKhauHashed;
    @Column(name = "role")
    private String Role;
    @Column(name = "sdt")
    private String SDT;
    @Column(name = "DiaChi")
    private String DiaChi;

    @Column(name = "TenHienThi")
    private String TenHienThi;
    @Column(name = "GioiTinh")
    private String GioiTinh;
    @Column(name = "TrangThai")
    private String TrangThai;


    public User() {
    }

    public User(Integer id, String tenDangNhap, String MatKhauHashed, String role, String SDT, String diaChi, String tenHienThi, String gioiTinh, String trangThai) {
        this.ID = id;
        this.TenDangNhap = tenDangNhap;
        this.MatKhauHashed = MatKhauHashed;
        this.Role = role;
        this.SDT = SDT;
        this.DiaChi = diaChi;

        this.TenHienThi = tenHienThi;
        this.GioiTinh = gioiTinh;
        this.TrangThai = trangThai;
    }

    public User(String tenDangNhap, String MatKhauHashed, String role, String SDT, String diaChi, String tenHienThi, String gioiTinh, String trangThai) {
        this.TenDangNhap = tenDangNhap;
        this.MatKhauHashed = MatKhauHashed;
        this.Role = role;
        this.SDT = SDT;
        this.DiaChi = diaChi;
        this.TenHienThi = tenHienThi;
        this.GioiTinh = gioiTinh;
        this.TrangThai = trangThai;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTenDangNhap() {
        return TenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.TenDangNhap = tenDangNhap;
    }

    public String getMatKhauHashed() {
        return MatKhauHashed;
    }

    public void setMatKhauHashed(String matKhauHashed) {
        this.MatKhauHashed = matKhauHashed;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        this.Role = role;
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
        this.DiaChi = diaChi;
    }


    public String getTenHienThi() {
        return TenHienThi;
    }

    public void setTenHienThi(String tenHienThi) {
        this.TenHienThi = tenHienThi;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.GioiTinh = gioiTinh;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", TenDangNhap='" + TenDangNhap + '\'' +
                ", MatKhauHashed='" + MatKhauHashed + '\'' +
                ", Role='" + Role + '\'' +
                ", SDT='" + SDT + '\'' +
                ", DiaChi='" + DiaChi + '\'' +
                ", TenHienThi='" + TenHienThi + '\'' +
                ", GioiTinh='" + GioiTinh + '\'' +
                ", TrangThai='" + TrangThai + '\'' +
                '}';
    }
}
