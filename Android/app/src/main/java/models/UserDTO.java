package models;

public class UserDTO {
    private String tenHienThi;
    private String sdt;
    private String diaChi;

    public UserDTO(String tenHienThi, String sdt, String diaChi) {
        this.tenHienThi = tenHienThi;
        this.sdt = sdt;
        this.diaChi = diaChi;
    }

    public UserDTO() {
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
}
