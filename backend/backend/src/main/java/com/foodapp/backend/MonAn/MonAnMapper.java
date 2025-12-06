package com.foodapp.backend.MonAn;

public class MonAnMapper {

    public static MonAnDTO toDTO (MonAn monAn){
        MonAnDTO dto = new MonAnDTO(
                monAn.getTenMonAn(),
                monAn.getMoTa(),
                monAn.getGia(),
                monAn.getQuocGia(),
                monAn.getTrangThai(),
                monAn.getHinhAnhURL()
        );
        return dto;
    }

}
