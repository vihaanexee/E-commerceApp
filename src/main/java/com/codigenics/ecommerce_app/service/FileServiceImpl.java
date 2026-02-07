package com.codigenics.ecommerce_app.service;

import com.codigenics.ecommerce_app.exceptions.APIException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        String ogFileName = file.getOriginalFilename();
        //make sure the path is png or jpeg and not any sh file or something
        String extension = ogFileName.substring(ogFileName.lastIndexOf(".")).toLowerCase();
        if (!extension.equals(".png") && !extension.equals(".jpg") && !extension.equals(".jpeg")){
            throw new APIException("Only PNG and JPEG files are allowed");
        }

        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(extension);

        String filePath = path + File.separator + fileName;

        //check if path exists
        File folder = new File(path);
        if (!folder.exists())
            folder.mkdirs();
        //upload to server

        Files.copy(file.getInputStream() , Paths.get(filePath));

        return fileName;
    }
}
