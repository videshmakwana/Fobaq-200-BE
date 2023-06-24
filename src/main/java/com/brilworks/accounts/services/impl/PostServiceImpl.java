package com.brilworks.accounts.services.impl;

import com.brilworks.accounts.controller.RecStatus;
import com.brilworks.accounts.controller.Status;
import com.brilworks.accounts.dto.PostDto;
import com.brilworks.accounts.dto.ResponseDto;
import com.brilworks.accounts.entity.PostDetails;
import com.brilworks.accounts.repository.PostRepository;
import com.brilworks.accounts.services.PostService;
import com.brilworks.accounts.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Override
    public ResponseDto createPost(PostDto postDto) {
        PostDetails postDetail=new PostDetails();
        if (postDto.getPostId()!=null) {
            Optional<PostDetails> postDetails = postRepository.findById(postDto.getPostId());
            if (postDetails.isPresent()){
                postDetail=postDetails.get();
                postDetail.setPostContent(postDto.getPostContent());
                postDetail.setWidth(postDto.getWidth());
                postDetail.setHeight(postDto.getHeight());
                postDetail.setImageURL(postDto.getImageURL());
                postDetail.setTags(postDto.getTags());
                postDetail.setUserId(postDto.getUserId());
                postDetail.setFacebook(postDto.isFacebook());
                postDetail.setInstagram(postDto.isInstagram());
                postDetail.setLinkedIn(postDto.isLinkedIn());
                postDetail.setSchedulerTime(postDto.getSchedulerTime());
                postDetail.setStatus(Status.UPDATED);
                postRepository.save(postDetail);
            }
        }
        else {
            postDetail.setPostContent(postDto.getPostContent());
            postDetail.setWidth(postDto.getWidth());
            postDetail.setHeight(postDto.getHeight());
            postDetail.setImageURL(postDto.getImageURL());
            postDetail.setTags(postDto.getTags());
            postDetail.setUserId(postDto.getUserId());
            postDetail.setFacebook(postDto.isFacebook());
            postDetail.setInstagram(postDto.isInstagram());
            postDetail.setLinkedIn(postDto.isLinkedIn());
            postDetail.setSchedulerTime(postDto.getSchedulerTime());
            postDetail.setStatus(Status.CREATED);
            postRepository.save(postDetail);
        }
        return new ResponseDto(Constants.SUCCESS, Constants.UPDATED);
    }

    @Override
    public List<PostDetails> getAllPostByUser(Long userId) {
        return postRepository.getAllPostByUser(userId);
    }
}
