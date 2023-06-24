package com.brilworks.accounts.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class ImageDownloadUtil implements ImageDownloadUtilService{
    @Autowired
    PostService  postService;
    @Override
    @Transactional
    public String downloadImage(Long postId, String imageUrl){

        String ext = "dat";
        String imageName = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);
        String destinationPath = "src/main/resources/images/"+imageName+".jpg";
        postService.saveImageName(postId,imageName);
        try {
            postService.downloadImage(imageUrl, destinationPath);
            return "Image downloaded successfully!";
        } catch (IOException e) {
            return "Failed to download the image.";
        }

    }
}
