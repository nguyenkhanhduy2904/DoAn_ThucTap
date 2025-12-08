package com.foodapp.backend.ImageUpload;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ImageUploadService {


    private final String ROOT_PATH = "uploads/images/";

    public ImageUpload addImage(MultipartFile file, String type) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Validate type
        if (!List.of("food", "avatar", "voucher").contains(type)) {
            throw new IllegalArgumentException("Invalid image type");
        }

        // Create folder path: uploads/images/food/
        Path uploadPath = Paths.get(ROOT_PATH + type);

        // If folder does not exist → create it
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Create unique file name
        String originalName = file.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf(".")); // .png, .jpg
        String fileName = UUID.randomUUID().toString() + extension;

        // Full path
        Path filePath = uploadPath.resolve(fileName);

        // Save file
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Build response object
        ImageUpload img = new ImageUpload();
        img.setImageName(fileName);
        img.setImageContent(file.getContentType());
        img.setSize(file.getSize());
        img.setUrl("/uploads/images/" + type + "/" + fileName);

        return img;


    }
}
