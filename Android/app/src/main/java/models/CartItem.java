package models;

public class CartItem {
    private MonAn monAn;
    private int quantity;
    private String note;

    public CartItem(MonAn monAn, int quantity, String note) {
        this.monAn = monAn;
        this.quantity = quantity;
        this.note = note;
    }

    public CartItem() {
    }

    public MonAn getMonAn() {
        return monAn;
    }

    public void setMonAn(MonAn monAn) {
        this.monAn = monAn;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
