package com.foodapp.backend.order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.foodapp.backend.orderItem.OrderItem;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TenNguoiNhan")
    private String tenNguoiNhan;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "SDT")
    private String sdt;

    @Column(name = "ThoiGianTao")
    private Date thoiGianTao;

    @Column(name = "TrangThai")
    private String trangThai;

    @Column(name = "TongTien")
    private BigDecimal tongTien;

    @Column(name = "users_ID")
    private Integer idKhachHang;



    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderItem> items = new ArrayList<>();





    public Order() {
        this.items = new ArrayList<>();
    }

    public Order(String tenNguoiNhan, String diaChi, String sdt, Date thoiGianTao,
                 String trangThai, BigDecimal tongTien, Integer idKhachHang, List<OrderItem> items) {
        this.tenNguoiNhan = tenNguoiNhan;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.thoiGianTao = thoiGianTao;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
        this.idKhachHang = idKhachHang;
        this.items = items != null ? items : new ArrayList<>();
    }

    public Order(Integer id, String tenNguoiNhan, String diaChi, String sdt, Date thoiGianTao, String trangThai, BigDecimal tongTien, Integer idKhachHang, List<OrderItem> items) {
        this.id = id;
        this.tenNguoiNhan = tenNguoiNhan;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.thoiGianTao = thoiGianTao;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
        this.idKhachHang = idKhachHang;
        this.items = items != null ? items : new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Date getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(Date thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Integer getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(Integer idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", tenKhachHang='" + tenNguoiNhan + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", sdt='" + sdt + '\'' +
                ", thoiGianTao=" + thoiGianTao +
                ", trangThai='" + trangThai + '\'' +
                ", tongTien=" + tongTien +
                ", idKhachHang=" + idKhachHang +
                '}';
    }
}
