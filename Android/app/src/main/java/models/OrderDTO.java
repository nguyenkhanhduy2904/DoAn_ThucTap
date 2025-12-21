package models;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderDTO {
    // Tên biến phải GIỐNG HỆT tên field trong Backend (Order.java)
    private Integer id;
    private String tenNguoiNhan;
    private String diaChi;
    private String sdt;
    private Date thoiGianTao;
    private String trangThaiDonHang;
    private String trangThaiThanhToan;
    private BigDecimal tongTien;
    private Integer idKhachHang; // Khớp với "idKhachHang" trong backend

    // Quan trọng: Danh sách món ăn gửi kèm
    private List<OrderItemDTO> items;

    public OrderDTO(Integer id, String tenNguoiNhan, String diaChi, String sdt, Date thoiGianTao, String trangThaiDonHang, String trangThaiThanhToan, BigDecimal tongTien, Integer idKhachHang, List<OrderItemDTO> items) {
        this.id = id;
        this.tenNguoiNhan = tenNguoiNhan;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.thoiGianTao = thoiGianTao;
        this.trangThaiDonHang = trangThaiDonHang;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.tongTien = tongTien;
        this.idKhachHang = idKhachHang;
        this.items = items;
    }

    public OrderDTO(String tenNguoiNhan, String diaChi, String sdt, Date thoiGianTao, String trangThaiDonHang, String trangThaiThanhToan, BigDecimal tongTien, Integer idKhachHang, List<OrderItemDTO> items) {
        this.tenNguoiNhan = tenNguoiNhan;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.thoiGianTao = thoiGianTao;
        this.trangThaiDonHang = trangThaiDonHang;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.tongTien = tongTien;
        this.idKhachHang = idKhachHang;
        this.items = items;
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

    public String getTrangThaiDonHang() {
        return trangThaiDonHang;
    }

    public void setTrangThaiDonHang(String trangThaiDonHang) {
        this.trangThaiDonHang = trangThaiDonHang;
    }

    public String getTrangThaiThanhToan() {
        return trangThaiThanhToan;
    }

    public void setTrangThaiThanhToan(String trangThaiThanhToan) {
        this.trangThaiThanhToan = trangThaiThanhToan;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public Integer getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(Integer idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }


}
