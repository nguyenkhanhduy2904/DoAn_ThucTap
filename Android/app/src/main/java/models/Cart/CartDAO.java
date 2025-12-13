package models.Cart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ttcn_dangnhap.Cart;

import java.util.ArrayList;
import java.util.List;
public class CartDAO {
    private final CartDbHelper dbHelper;

    public CartDAO(CartDbHelper cartDbHelper) {
        this.dbHelper = cartDbHelper;
    }

    public long addItem(CartItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("user_id", item.getUserid());
        values.put("ten_mon", item.getTenMon());
        values.put("so_luong", item.getSoLuong());
        values.put("ghi_chu", item.getGhiChu());
        values.put("gia_tung_mon", item.getGiaTungMon());
        values.put("gia_tong_mon", item.getGiaTongMon());
        values.put("monan_id", item.getMonanid());

        return db.insert("cart_items", null, values);
    }

    public List<CartItem> getCartByUser(int userId) {
        List<CartItem> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM cart_items WHERE user_id=?",
                new String[]{String.valueOf(userId)}
        );

        if (cursor.moveToFirst()) {
            do {
                CartItem item = new CartItem();

                item.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                item.setUserid(cursor.getInt(cursor.getColumnIndexOrThrow("user_id")));
                item.setTenMon(cursor.getString(cursor.getColumnIndexOrThrow("ten_mon")));
                item.setSoLuong(cursor.getInt(cursor.getColumnIndexOrThrow("so_luong")));
                item.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow("ghi_chu")));
                item.setGiaTungMon(cursor.getLong(cursor.getColumnIndexOrThrow("gia_tung_mon")));
                item.setGiaTongMon(cursor.getLong(cursor.getColumnIndexOrThrow("gia_tong_mon")));
                item.setMonanid(cursor.getInt(cursor.getColumnIndexOrThrow("monan_id")));
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


    public CartItem getItemForUser(int userId, int monAnId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM cart_items WHERE user_id = ? AND monan_id = ?",
                new String[]{String.valueOf(userId), String.valueOf(monAnId)}
        );

        if (cursor.moveToFirst()) {
            CartItem item = new CartItem();
            item.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            item.setUserid(cursor.getInt(cursor.getColumnIndexOrThrow("user_id")));
            item.setTenMon(cursor.getString(cursor.getColumnIndexOrThrow("ten_mon")));
            item.setSoLuong(cursor.getInt(cursor.getColumnIndexOrThrow("so_luong")));
            item.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow("ghi_chu")));
            item.setGiaTungMon(cursor.getLong(cursor.getColumnIndexOrThrow("gia_tung_mon")));
            item.setGiaTongMon(cursor.getLong(cursor.getColumnIndexOrThrow("gia_tong_mon")));
            item.setMonanid(cursor.getInt(cursor.getColumnIndexOrThrow("monan_id")));
            cursor.close();
            return item;
        }

        cursor.close();
        return null;
    }

    public List<CartItem> getAllItemWithThisUserAndMonAnId(int userId, int monAnId){
        List<CartItem> result = new ArrayList<>();


        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM cart_items WHERE user_id = ? AND monan_id = ?",
                new String[]{String.valueOf(userId), String.valueOf(monAnId)}
        );

        while (cursor.moveToNext()) {
            CartItem item = new CartItem();
            item.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            item.setUserid(cursor.getInt(cursor.getColumnIndexOrThrow("user_id")));
            item.setTenMon(cursor.getString(cursor.getColumnIndexOrThrow("ten_mon")));
            item.setSoLuong(cursor.getInt(cursor.getColumnIndexOrThrow("so_luong")));
            item.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow("ghi_chu")));
            item.setGiaTungMon(cursor.getLong(cursor.getColumnIndexOrThrow("gia_tung_mon")));
            item.setGiaTongMon(cursor.getLong(cursor.getColumnIndexOrThrow("gia_tong_mon")));
            item.setMonanid(cursor.getInt(cursor.getColumnIndexOrThrow("monan_id")));
            result.add(item);
        }
        cursor.close();
        return result;
    }

//    public void update(int userId, int monAnId, int newQty, long newTotal) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put("soLuong", newQty);
//        cv.put("giaTongMon", newTotal);
//
//        db.update("cart", cv, "user_id=? AND monan_id=?",
//                new String[]{String.valueOf(userId), String.valueOf(monAnId)});
//    }

    public boolean update(CartItem cartItem){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("so_luong", cartItem.getSoLuong());
        cv.put("gia_tong_mon",cartItem.getGiaTongMon());
        cv.put("ghi_chu",cartItem.getGhiChu());

        String[] args = {String.valueOf(cartItem.getId())};
        int rows = db.update("cart_items", cv, "id=?", args);
        return rows > 0;
    }

    public boolean delete(int cartItemid){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] args = {String.valueOf(cartItemid)};
        int rows = db.delete("cart_items","id=?", args);
        return rows >0;

    }

    public void clearCart(int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("cart_items", "user_id=?", new String[]{String.valueOf(userId)});
    }


}
