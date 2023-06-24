package com.brilworks.accounts.controller;

import com.brilworks.accounts.dto.*;
import com.brilworks.accounts.entity.PostDetails;
import com.brilworks.accounts.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/rest/accounts/posts")
@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private AuthValidator authValidator;
    @PutMapping()
    public ResponseDto createPost(@RequestBody PostDto postDto, Authentication authorization) {
        authValidator.authUser(authorization);
        return postService.createPost(postDto);
    }
    @GetMapping()
    public String createPost1( Authentication authorization) {
        authValidator.authUser(authorization);
        return "postService.createPost(postDto,postId);";
    }

    @GetMapping(value = "/{userId}")
    public List<PostDetails> getAllPostByUser(@PathVariable("userId")Long userId){
        return postService.getAllPostByUser(userId);
    }
}
