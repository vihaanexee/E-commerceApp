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
//Getting the original file name as uploaded by the user
        String ogFileName = file.getOriginalFilename();

        //Getting the file extension to check if it is png of jpg or jpeg
        String extension = ogFileName.substring(ogFileName.lastIndexOf(".")).toLowerCase();
        if (!extension.equals(".png") && !extension.equals(".jpg") && !extension.equals(".jpeg")){
            throw new APIException("Only PNG and JPEG files are allowed");
        }
        //Generating a Unique id for the image
        String randomId = UUID.randomUUID().toString();

        //add the extension to the unique id
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
