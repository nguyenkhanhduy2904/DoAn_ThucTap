package com.foodapp.backend.MonAn;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class MonAnService {
    private final MonAnRepository monAnRepository;

    @Autowired
    public MonAnService(MonAnRepository monAnRepository) {
        this.monAnRepository = monAnRepository;
    }

    public List<MonAnDTO> getAllMonAn() {
        return monAnRepository.findAll().stream().map(MonAnMapper::toDTO).toList();
    }

    public MonAnDTO getMonAnByID(Integer monAnid) {
        MonAn monAn = monAnRepository.findById(monAnid).orElseThrow(()-> new IllegalStateException("MonAn with id: "+ monAnid + " doesnt exist"));
        return MonAnMapper.toDTO(monAn);
    }

    public void deleteMonAn(Integer monAnid) {
        boolean isMonAnExist = monAnRepository.existsById(monAnid);
        if(!isMonAnExist){
            throw new IllegalStateException("MonAn with id: "+monAnid +" doesnt exist");
        }
        monAnRepository.deleteById(monAnid);
    }

    public void addMonAn(MonAn monAn) {
        System.out.println(monAn);
        monAnRepository.save(monAn);
    }

    @Transactional
    public void updateMonAn(Integer monAnid, String tenMonAn, String moTa, BigDecimal gia, String quocGia, String trangThai, String hinhAnhURL) {
        MonAn monAn = monAnRepository.findById(monAnid).orElseThrow(()-> new IllegalStateException("MonAn with id: "+ monAnid+ " doesnt exist"));

        //update TenMonAn
        if(tenMonAn !=null && !tenMonAn.isBlank() && !Objects.equals(monAn.getTenMonAn(),tenMonAn)){
            monAn.setTenMonAn(tenMonAn);

        }

        // Update MoTa
        if (moTa != null && !moTa.isBlank()
                && !Objects.equals(monAn.getMoTa(), moTa)) {
            monAn.setMoTa(moTa);
        }

        // Update Gia
        if (gia != null
                && (monAn.getGia() == null || monAn.getGia().compareTo(gia) != 0)) {
            monAn.setGia(gia);
        }

        // Update QuocGia
        if (quocGia != null && !quocGia.isBlank()
                && !Objects.equals(monAn.getQuocGia(), quocGia)) {
            monAn.setQuocGia(quocGia);
        }

        // Update TrangThai
        if (trangThai != null && !trangThai.isBlank()
                && !Objects.equals(monAn.getTrangThai(), trangThai)) {
            monAn.setTrangThai(trangThai);
        }

        // Update HinhAnhURL
        if (hinhAnhURL != null && !hinhAnhURL.isBlank()
                && !Objects.equals(monAn.getHinhAnhURL(), hinhAnhURL)) {
            monAn.setHinhAnhURL(hinhAnhURL);
        }

        // Save updated data
        monAnRepository.save(monAn);
    }
}
