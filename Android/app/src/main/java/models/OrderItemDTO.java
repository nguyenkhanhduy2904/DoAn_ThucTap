package models;
import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItemDTO implements Serializable {
    // Tên biến phải GIỐNG HỆT tên field trong Backend (OrderItem.java)
    private String tenMon;
    private int soLuong;
    private String ghiChu;
    private BigDecimal giaTungMon;
    private BigDecimal giaTongMon;
    private Integer monanid; // Khớp với "monanid" trong backend

    public OrderItemDTO(String tenMon, int soLuong, String ghiChu, BigDecimal giaTungMon, BigDecimal giaTongMon, Integer monanid) {
        this.tenMon = tenMon;
        this.soLuong = soLuong;
        this.ghiChu = ghiChu;
        this.giaTungMon = giaTungMon;
        this.giaTongMon = giaTongMon;
        this.monanid = monanid;
    }

    public OrderItemDTO() {
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public BigDecimal getGiaTungMon() {
        return giaTungMon;
    }

    public void setGiaTungMon(BigDecimal giaTungMon) {
        this.giaTungMon = giaTungMon;
    }

    public BigDecimal getGiaTongMon() {
        return giaTongMon;
    }

    public void setGiaTongMon(BigDecimal giaTongMon) {
        this.giaTongMon = giaTongMon;
    }

    public Integer getMonanid() {
        return monanid;
    }

    public void setMonanid(Integer monanid) {
        this.monanid = monanid;
    }
}
