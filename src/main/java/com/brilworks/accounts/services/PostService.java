package com.brilworks.accounts.services;

import com.brilworks.accounts.dto.PostDto;
import com.brilworks.accounts.dto.ResponseDto;
import com.brilworks.accounts.entity.PostDetails;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public interface PostService {
     ResponseDto createPost(PostDto postDto);

     List<PostDetails> getAllPostByUser(Long userId);
}
