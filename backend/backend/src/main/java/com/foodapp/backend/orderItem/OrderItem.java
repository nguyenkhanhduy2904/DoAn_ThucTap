package com.foodapp.backend.orderItem;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.foodapp.backend.order.Order;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TenMon")
    private String tenMon;

    @Column(name = "SoLuong")
    private int soLuong;

    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "GiaTungMon")
    private BigDecimal giaTungMon;

    @Column(name = "GiaTongMon")
    private BigDecimal giaTongMon;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_ID")
    @JsonBackReference
    private Order order;
    @Column(name = "monan_ID")
    private Integer monanid;

    public OrderItem() {}

    // getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTenMon() { return tenMon; }
    public void setTenMon(String tenMon) { this.tenMon = tenMon; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public BigDecimal getGiaTungMon() { return giaTungMon; }
    public void setGiaTungMon(BigDecimal giaTungMon) { this.giaTungMon = giaTungMon; }

    public BigDecimal getGiaTongMon() { return giaTongMon; }
    public void setGiaTongMon(BigDecimal giaTongMon) { this.giaTongMon = giaTongMon; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Integer getMonanid() { return monanid; }
    public void setMonanid(Integer monanid) { this.monanid = monanid; }



}
