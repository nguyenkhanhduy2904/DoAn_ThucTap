package com.foodapp.backend.MonAn;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/monan")
public class MonAnController {
    private final MonAnService monAnService;

    @Autowired
    public MonAnController(MonAnService monAnService) {
        this.monAnService = monAnService;
    }

    @GetMapping
    public List<MonAnDTO> getAllMonAn(){
        return monAnService.getAllMonAn();
    }

    @GetMapping(path = "{monAnid}")
    public MonAnDTO getMonAnByID(@PathVariable("monAnid") Integer monAnid){
        return monAnService.getMonAnByID(monAnid);

    }

    @PostMapping()
    public void addMonAn(@RequestBody MonAn monAn){
        monAnService.addMonAn(monAn);

    }


    @DeleteMapping(path = "{monAnid}")
    public void deleteMonAn(@PathVariable("monAnid") Integer monAnid){
        monAnService.deleteMonAn(monAnid);
    }

    @PutMapping(path = "{monAnid}")
    public void updateMonAn(@PathVariable("monAnid") Integer monAnid,
                            @RequestParam(required = false) String TenMonAn,
                            @RequestParam(required = false) String MoTa,
                            @RequestParam(required = false)BigDecimal Gia,
                            @RequestParam(required = false) String QuocGia,
                            @RequestParam(required = false) String TrangThai,
                            @RequestParam(required = false) String HinhAnhURL){

        monAnService.updateMonAn(monAnid, TenMonAn, MoTa, Gia, QuocGia, TrangThai, HinhAnhURL);

    }
}
