package models;

import java.io.Serializable;
import java.util.Date;

public class MaGiamGia implements Serializable {
    private int idMa;
    private String tenMa;
    private Date ngayTao;
    private Date ngayHetHan;//probably need to rework on this

    public MaGiamGia(String tenMa, int idMa, Date ngayTao, Date ngayHetHan) {
        this.tenMa = tenMa;
        this.idMa = idMa;
        this.ngayTao = ngayTao;
        this.ngayHetHan = ngayHetHan;
    }

    public int getIdMa() {
        return idMa;
    }

    public void setIdMa(int idMa) {
        this.idMa = idMa;
    }

    public String getTenMa() {
        return tenMa;
    }

    public void setTenMa(String tenMa) {
        this.tenMa = tenMa;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Date getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(Date ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    @Override
    public String toString() {
        return "MaGiamGia{" +
                "idMa=" + idMa +
                ", tenMa='" + tenMa + '\'' +
                ", ngayTao=" + ngayTao +
                ", ngayHetHan=" + ngayHetHan +
                '}';
    }
}
