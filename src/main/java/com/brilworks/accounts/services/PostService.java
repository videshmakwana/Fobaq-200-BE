package com.brilworks.accounts.services;

import com.brilworks.accounts.dto.PostDto;
import com.brilworks.accounts.dto.ResponseDto;
import com.brilworks.accounts.entity.PostDetails;
import org.springframework.stereotype.Component;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@Component
public interface PostService {
     ResponseDto createPost(PostDto postDto);

     List<PostDetails> getAllPostByUser(Long userId);

    void downloadImage(String imageUrl, String destinationPath)throws IOException;

    void saveImageName(Long postId, String imageName);
}
