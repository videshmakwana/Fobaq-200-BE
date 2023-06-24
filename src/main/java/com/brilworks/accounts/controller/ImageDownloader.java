package com.brilworks.accounts.controller;

import com.brilworks.accounts.services.PostService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.io.IOException;

@RequestMapping("/downloadImage")
@Controller
public class ImageDownloader {

    @Autowired
    PostService  postService;

    @PostMapping
    public String downloadImage(){


        String ext = "dat";
        String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);
        String destinationPath = "src/main/resources/images/"+name+".jpg";
        try {
            postService.downloadImage("https://oaidalleapiprodscus.blob.core.windows.net/private/org-DlFu8fODJQpnuB2ANuLrM05L/user-NxJG2ki8DHlL7z5Tu2FlINtI/img-z9tui3zHk2PsEYsLGC4KHeu3.png?st=2023-06-24T15%3A53%3A18Z&se=2023-06-24T17%3A53%3A18Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2023-06-24T01%3A51%3A48Z&ske=2023-06-25T01%3A51%3A48Z&sks=b&skv=2021-08-06&sig=4N/qITAp9Uhlx9SB44VU4j5mjCLI1lrA0ouB4%2BGehR8%3D", destinationPath);
            return "Image downloaded successfully!";
        } catch (IOException e) {
            return "Failed to download the image.";
        }
    }
}
