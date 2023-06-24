package com.brilworks.accounts.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;


@Service
public class FileUploadS3Bucket {
    String endPointUrl = "https://s3.ap-south-1.amazonaws.com";
    String bucketName = "brilcrm-be";
    @Autowired
    private AmazonS3 amazonS3;

    public String uploadFileToS3Bucket(final MultipartFile multipartFile, String folderName) {
        String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        final String generatedFileName = generateFileName(multipartFile);
        File file = convertMultiPartFileToFile(multipartFile);
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName + "/" + folderName, generatedFileName + "." + ext, file);
        amazonS3.putObject(putObjectRequest);
        return endPointUrl + "/" + bucketName + "/" + folderName + "/" + generatedFileName + "." + ext;
    }


    private File convertMultiPartFileToFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }
        final File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return file;
    }

    private String generateFileName(MultipartFile file) {
        return LocalDateTime.now() + "_" + file.getName();
    }

}
