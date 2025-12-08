package com.foodapp.backend.ImageUpload;

import com.foodapp.backend.Response.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(path = "api/v1/upload")
public class ImageUploadController {

    private final ImageUploadService imageUploadService;

    @Autowired
    public ImageUploadController(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
    }

    @PostMapping(path = "image")
    public ResponseEntity<APIResponse<ImageUpload>> uploadImage(@RequestParam("file")MultipartFile file,
                                                                @RequestParam("type")String type){

        try{
            ImageUpload response = imageUploadService.addImage(file, type);
            return ResponseEntity.ok( new APIResponse<>(
                    "success",
                    200,
                    "Image upload success",
                    response
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse<>(
                    "error",
                    500,
                    e.getMessage(),
                    null
            ));
        }


    }


}
