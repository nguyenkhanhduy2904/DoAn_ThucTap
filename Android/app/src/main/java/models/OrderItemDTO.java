package models;
import java.math.BigDecimal;

public class OrderItemDTO {
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
}
