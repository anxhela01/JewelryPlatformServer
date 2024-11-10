package com.example.ECommercePlatform.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ImageService {

    public String saveImage(File path, MultipartFile Image, String imageName) throws IOException {

        File uploadRootDir = new File(String.valueOf(path));

        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }

        try {
            String originalFilename = Image.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = imageName + extension;
            File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + fileName);


            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(Image.getBytes());
            stream.close();
            return serverFile.getName();


        } catch (IOException e) {
            throw new IOException("Problem with file saving");
        }
    }




}
