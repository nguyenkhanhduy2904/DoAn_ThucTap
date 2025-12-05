package models;

import java.io.Serializable;




public class MonAn implements Serializable {
    private int idMonAn;
    private String tenMonAn;
    private String motaMonAn;
    private long giaMonAn;
    private String urlHinhAnhMonAn;
    private EQuocGia quocGia;
    private boolean trangThai;


    public MonAn(int idMonAn, String tenMonAn, String motaMonAn, long giaMonAn, String urlHinhAnhMonAn, EQuocGia quocGia, boolean trangThai) {
        this.idMonAn = idMonAn;
        this.tenMonAn = tenMonAn;
        this.motaMonAn = motaMonAn;
        this.giaMonAn = giaMonAn;
        this.urlHinhAnhMonAn = urlHinhAnhMonAn;
        this.quocGia = quocGia;
        this.trangThai = trangThai;
    }

    public int getIdMonAn() {
        return idMonAn;
    }

    public void setIdMonAn(int idMonAn) {
        this.idMonAn = idMonAn;
    }

    public String getMotaMonAn() {
        return motaMonAn;
    }

    public void setMotaMonAn(String motaMonAn) {
        this.motaMonAn = motaMonAn;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public long getGiaMonAn() {
        return giaMonAn;
    }

    public void setGiaMonAn(long giaMonAn) {
        this.giaMonAn = giaMonAn;
    }

    public String getUrlHinhAnhMonAn() {
        return urlHinhAnhMonAn;
    }

    public void setUrlHinhAnhMonAn(String urlHinhAnhMonAn) {
        this.urlHinhAnhMonAn = urlHinhAnhMonAn;
    }

    public EQuocGia getQuocGia() {
        return quocGia;
    }

    public void setQuocGia(EQuocGia quocGia) {
        this.quocGia = quocGia;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "MonAn{" +
                "idMonAn='" + idMonAn + '\'' +
                ", tenMonAn='" + tenMonAn + '\'' +
                ", motaMonAn='" + motaMonAn + '\'' +
                ", giaMonAn=" + giaMonAn +
                ", urlHinhAnhMonAn='" + urlHinhAnhMonAn + '\'' +
                ", quocGia=" + quocGia +
                ", trangThai=" + trangThai +
                '}';
    }
}
