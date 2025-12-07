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

    public List<MonAn> getAllMonAn() {

        return monAnRepository.findAll();
    }

    public MonAn getMonAnByID(Integer monAnid) {
        return monAnRepository.findById(monAnid).orElseThrow(()-> new IllegalStateException("MonAn with id: "+ monAnid + " doesnt exist"));

    }

    public void deleteMonAn(Integer monAnid) {
        boolean isMonAnExist = monAnRepository.existsById(monAnid);
        if(!isMonAnExist){
            throw new IllegalStateException("MonAn with id: "+monAnid +" doesnt exist");
        }
        monAnRepository.deleteById(monAnid);
    }

    public MonAn addMonAn(MonAn monAn) {

        if(monAn.getTenMonAn()!=null && !monAn.getTenMonAn().isBlank()){
            throw new IllegalStateException("TenMonAn cannot be null");
        }

        monAnRepository.save(monAn);
        return monAn;
    }

    public void updateMonAn(Integer monAnid, MonAn newDataMonAn) {

        MonAn existedMonAn = monAnRepository.findById(monAnid)
                .orElseThrow(() -> new IllegalStateException("MonAn with id: " + monAnid + " doesn't exist"));

        // Update TenMonAn
        if (newDataMonAn.getTenMonAn() != null &&
                !newDataMonAn.getTenMonAn().isBlank() &&
                !Objects.equals(newDataMonAn.getTenMonAn(), existedMonAn.getTenMonAn())) {

            existedMonAn.setTenMonAn(newDataMonAn.getTenMonAn());
        }

        // Update MoTa
        if (newDataMonAn.getMoTa() != null &&
                !newDataMonAn.getMoTa().isBlank() &&
                !Objects.equals(newDataMonAn.getMoTa(), existedMonAn.getMoTa())) {

            existedMonAn.setMoTa(newDataMonAn.getMoTa());
        }

        // Update Gia
        if (newDataMonAn.getGia() != null &&
                (existedMonAn.getGia() == null ||
                        existedMonAn.getGia().compareTo(newDataMonAn.getGia()) != 0)) {

            existedMonAn.setGia(newDataMonAn.getGia());
        }

        // Update QuocGia
        if (newDataMonAn.getQuocGia() != null &&
                !newDataMonAn.getQuocGia().isBlank() &&
                !Objects.equals(newDataMonAn.getQuocGia(), existedMonAn.getQuocGia())) {

            existedMonAn.setQuocGia(newDataMonAn.getQuocGia());
        }

        // Update TrangThai
        if (newDataMonAn.getTrangThai() != null &&
                !newDataMonAn.getTrangThai().isBlank() &&
                !Objects.equals(newDataMonAn.getTrangThai(), existedMonAn.getTrangThai())) {

            existedMonAn.setTrangThai(newDataMonAn.getTrangThai());
        }

        // Update HinhAnhURL
        if (newDataMonAn.getHinhAnhURL() != null &&
                !newDataMonAn.getHinhAnhURL().isBlank() &&
                !Objects.equals(newDataMonAn.getHinhAnhURL(), existedMonAn.getHinhAnhURL())) {

            existedMonAn.setHinhAnhURL(newDataMonAn.getHinhAnhURL());
        }


        monAnRepository.save(existedMonAn);
    }


//    @Transactional
//    public void updateMonAn(Integer monAnid, String tenMonAn, String moTa, BigDecimal gia, String quocGia, String trangThai, String hinhAnhURL) {
//        MonAn monAn = monAnRepository.findById(monAnid).orElseThrow(()-> new IllegalStateException("MonAn with id: "+ monAnid+ " doesnt exist"));
//
//        //update TenMonAn
//        if(tenMonAn !=null && !tenMonAn.isBlank() && !Objects.equals(monAn.getTenMonAn(),tenMonAn)){
//            monAn.setTenMonAn(tenMonAn);
//
//        }
//
//        // Update MoTa
//        if (moTa != null && !moTa.isBlank()
//                && !Objects.equals(monAn.getMoTa(), moTa)) {
//            monAn.setMoTa(moTa);
//        }
//
//        // Update Gia
//        if (gia != null
//                && (monAn.getGia() == null || monAn.getGia().compareTo(gia) != 0)) {
//            monAn.setGia(gia);
//        }
//
//        // Update QuocGia
//        if (quocGia != null && !quocGia.isBlank()
//                && !Objects.equals(monAn.getQuocGia(), quocGia)) {
//            monAn.setQuocGia(quocGia);
//        }
//
//        // Update TrangThai
//        if (trangThai != null && !trangThai.isBlank()
//                && !Objects.equals(monAn.getTrangThai(), trangThai)) {
//            monAn.setTrangThai(trangThai);
//        }
//
//        // Update HinhAnhURL
//        if (hinhAnhURL != null && !hinhAnhURL.isBlank()
//                && !Objects.equals(monAn.getHinhAnhURL(), hinhAnhURL)) {
//            monAn.setHinhAnhURL(hinhAnhURL);
//        }
//
//        // Save updated data
//        monAnRepository.save(monAn);
//    }
}
