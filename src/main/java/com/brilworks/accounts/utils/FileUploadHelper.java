package com.brilworks.accounts.utils;

import com.brilworks.accounts.exception.NotFoundException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Objects;

@Component
public class FileUploadHelper {



    String path = new ClassPathResource("/src/main/resources/Images").getPath();

    public String generateFileName(MultipartFile multipartFile) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multipartFile.getOriginalFilename()).replace(" ", "_");
    }


    public ResponseEntity<Resource> getMultipartFile(String fileUrl) throws IOException {
        if (fileUrl.isEmpty()) {
            throw new NotFoundException(NotFoundException.NotFound.FILE_NOT_FOUND);
        }
        byte[] array = Files.readAllBytes(Paths.get(fileUrl));
        HttpHeaders headers = new HttpHeaders();
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(array));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(array.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String fileName = generateFileName(multipartFile);
        File file = new File(path);
        if (!file.isDirectory()) {
            Files.createDirectories(Paths.get(path));
        }
        Files.copy(multipartFile.getInputStream(), Paths.get(path).resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        return path + "/" + fileName;
    }

}
