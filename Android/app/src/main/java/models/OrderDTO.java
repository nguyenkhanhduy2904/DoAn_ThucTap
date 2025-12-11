package models;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderDTO {
    // Tên biến phải GIỐNG HỆT tên field trong Backend (Order.java)
    private Integer id;
    private String tenKhachHang;
    private String diaChi;
    private String sdt;
    private Date thoiGianTao;
    private String trangThai;
    private BigDecimal tongTien;
    private Integer idKhachHang; // Khớp với "idKhachHang" trong backend

    // Quan trọng: Danh sách món ăn gửi kèm
    private List<OrderItemDTO> items;

    public OrderDTO(String tenKhachHang, String diaChi, String sdt,  BigDecimal tongTien, Integer idKhachHang, List<OrderItemDTO> items) {
        this.tenKhachHang = tenKhachHang;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.thoiGianTao = null;
        this.trangThai = "Pending"; // Mặc định trạng thái
        this.tongTien = tongTien;
        this.idKhachHang = idKhachHang;
        this.items = items;
    }

    public Integer getId() {
        return id;
    }
}
