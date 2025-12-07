package com.foodapp.backend.MonAn;


import com.foodapp.backend.Response.APIResponse;
import com.foodapp.backend.users.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @GetMapping
//    public List<MonAnDTO> getAllMonAn(){
//        return monAnService.getAllMonAn();
//    }

    @GetMapping
    public ResponseEntity<APIResponse<List<MonAn>>> getAllMonAn(){
        List<MonAn> allMonAn = monAnService.getAllMonAn();

        APIResponse<List<MonAn>> response = new APIResponse<>(
                "success",
                200,
                "Fetch success",
                allMonAn
        );

        return ResponseEntity.ok(response);
    }

//    @GetMapping(path = "{monAnid}")
//    public MonAnDTO getMonAnByID(@PathVariable("monAnid") Integer monAnid){
//        return monAnService.getMonAnByID(monAnid);
//
//    }

    @GetMapping(path = "{monAnid}")
    public ResponseEntity<APIResponse<MonAn>> getMonAnByID(@PathVariable("monAnid") Integer monAnid){
        try{
            return ResponseEntity.ok(new APIResponse<MonAn>(
                    "success",
                    200,
                    "Fetch success",
                    monAnService.getMonAnByID(monAnid)
            ));
        }

        catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(
                    "error",
                    404,
                    e.getMessage(),
                    null
            ));
        }
    }

//    @PostMapping()
//    public void addMonAn(@RequestBody MonAn monAn){
//        monAnService.addMonAn(monAn);
//
//    }

    @PostMapping
    public ResponseEntity<APIResponse<MonAn>> addMonAn(@RequestBody MonAn monAn){
        try{
            return ResponseEntity.ok(new APIResponse<>(
                    "success",
                    200,
                    "MonAn added success",
                    monAnService.addMonAn(monAn)
            ));
        }
        catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(
                    "error",
                    404,
                    e.getMessage(),
                    null
            ));
        }
    }


//    @DeleteMapping(path = "{monAnid}")
//    public void deleteMonAn(@PathVariable("monAnid") Integer monAnid){
//        monAnService.deleteMonAn(monAnid);
//    }

    @DeleteMapping(path = "{monAnid}")
    public ResponseEntity<APIResponse<Void>> deleteMonAn(@PathVariable("monAnid") Integer monAnid){
        try{
            monAnService.deleteMonAn(monAnid);
            return ResponseEntity.ok(new APIResponse<>(
                    "success",
                    200,
                    "MonAn delete success",
                    null

            ));
        }
        catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(
                    "error",
                    404,
                    e.getMessage(),
                    null
            ));
        }
    }

//    @PutMapping(path = "{monAnid}")
//    public void updateMonAn(@PathVariable("monAnid") Integer monAnid,
//                            @RequestParam(required = false) String TenMonAn,
//                            @RequestParam(required = false) String MoTa,
//                            @RequestParam(required = false)BigDecimal Gia,
//                            @RequestParam(required = false) String QuocGia,
//                            @RequestParam(required = false) String TrangThai,
//                            @RequestParam(required = false) String HinhAnhURL){
//
//        monAnService.updateMonAn(monAnid, TenMonAn, MoTa, Gia, QuocGia, TrangThai, HinhAnhURL);
//
//    }

    @PutMapping(path = "{monAnid}")
    public ResponseEntity<APIResponse<Void>> updateMonAn(@PathVariable("monAnid") Integer monAnid,
                                                         @RequestBody MonAn monAn){
        try {
            monAnService.updateMonAn(monAnid, monAn);

            APIResponse<Void> response = new APIResponse<>(
                    "success",
                    200,
                    "Update success",
                    null
            );

            return ResponseEntity.ok(response);
        }
        catch (IllegalStateException e) {

            APIResponse<Void> response = new APIResponse<>(
                    "Update failed",
                    404,
                    e.getMessage(),
                    null
            );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
