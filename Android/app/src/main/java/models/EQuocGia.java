package models;

import com.example.ttcn_dangnhap.R;

import java.io.Serializable;

public enum EQuocGia implements Serializable {
    VietNam(R.drawable.twemoji_flag_vietnam),
    ThaiLan(R.drawable.twemoji_flag_thailand),
    HanQuoc(R.drawable.twemoji_flag_south_korea),
    TrungQuoc(R.drawable.twemoji_flag_china);

    private final int drawableId;

    EQuocGia(int drawableId) {
        this.drawableId = drawableId;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public static EQuocGia StringtoEnum(String value) {
        for (EQuocGia qg : EQuocGia.values()) {
            if (qg.name().equalsIgnoreCase(value)) {
                return qg;
            }
        }
        return null; // or a default
    }
}
