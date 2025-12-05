package com.foodapp.backend.users;


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
    private String MatKhauHashed;
    @Column(name = "role")
    private String Role;
    @Column(name = "sdt")
    private String SDT;
    @Column(name = "DiaChi")
    private String DiaChi;
    @Column(name = "AvatarURL")
    private String AvatarURL;
    @Column(name = "TenHienThi")
    private String TenHienThi;
    @Column(name = "GioiTinh")
    private String GioiTinh;


    public User() {
    }

    public User(Integer id, String tenDangNhap, String MatKhauHashed, String role, String SDT, String diaChi, String avatarURL, String tenHienThi, String gioiTinh) {
        this.ID = id;
        this.TenDangNhap = tenDangNhap;
        this.MatKhauHashed = MatKhauHashed;
        this.Role = role;
        this.SDT = SDT;
        this.DiaChi = diaChi;
        this.AvatarURL = avatarURL;
        this.TenHienThi = tenHienThi;
        this.GioiTinh = gioiTinh;
    }

    public User(String tenDangNhap, String MatKhauHashed, String role, String SDT, String diaChi, String avatarURL, String tenHienThi, String gioiTinh) {
        this.TenDangNhap = tenDangNhap;
        this.MatKhauHashed = MatKhauHashed;
        this.Role = role;
        this.SDT = SDT;
        this.DiaChi = diaChi;
        this.AvatarURL = avatarURL;
        this.TenHienThi = tenHienThi;
        this.GioiTinh = gioiTinh;
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

    public String getAvatarURL() {
        return AvatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.AvatarURL = avatarURL;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + ID +
                ", tenDangNhap='" + TenDangNhap + '\'' +
                ", passwordHashed='" + MatKhauHashed + '\'' +
                ", role='" + Role + '\'' +
                ", SDT='" + SDT + '\'' +
                ", diaChi='" + DiaChi + '\'' +
                ", avatarURL='" + AvatarURL + '\'' +
                ", tenHienThi='" + TenHienThi + '\'' +
                ", gioiTinh='" + GioiTinh + '\'' +
                '}';
    }
}
