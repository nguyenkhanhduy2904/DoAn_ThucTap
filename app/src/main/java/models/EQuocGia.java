package models;

import com.example.ttcn_dangnhap.R;

public enum EQuocGia {
    VietNam(R.drawable.twemoji_flag_vietnam),
    ThaiLan(R.drawable.twemoji_flag_thailand),
    HanQuoc(R.drawable.twemoji_flag_south_korea);

    private final int drawableId;

    EQuocGia(int drawableId) {
        this.drawableId = drawableId;
    }

    public int getDrawableId() {
        return drawableId;
    }
}
