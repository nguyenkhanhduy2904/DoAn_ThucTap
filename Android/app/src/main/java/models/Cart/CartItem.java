package models.Cart;

public class CartItem {
    private int id;
    private String tenMon;
    private int soLuong;
    private String ghiChu;
    private long giaTungMon;
    private long giaTongMon;
    private int monanid;
    private int userid;

    public CartItem() {
    }

    public CartItem(int id, String tenMon, int soLuong, String ghiChu, long giaTungMon, long giaTongMon, int monanid, int userid) {
        this.id = id;
        this.tenMon = tenMon;
        this.soLuong = soLuong;
        this.ghiChu = ghiChu;
        this.giaTungMon = giaTungMon;
        this.giaTongMon = giaTongMon;
        this.monanid = monanid;
        this.userid = userid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public long getGiaTungMon() {
        return giaTungMon;
    }

    public void setGiaTungMon(long giaTungMon) {
        this.giaTungMon = giaTungMon;
    }

    public long getGiaTongMon() {
        return giaTongMon;
    }

    public void setGiaTongMon(long giaTongMon) {
        this.giaTongMon = giaTongMon;
    }

    public int getMonanid() {
        return monanid;
    }

    public void setMonanid(int monanid) {
        this.monanid = monanid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
