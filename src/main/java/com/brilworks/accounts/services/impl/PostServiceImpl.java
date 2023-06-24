package com.brilworks.accounts.services.impl;

import com.brilworks.accounts.controller.RecStatus;
import com.brilworks.accounts.controller.Status;
import com.brilworks.accounts.dto.PostDto;
import com.brilworks.accounts.dto.ResponseDto;
import com.brilworks.accounts.entity.PostDetails;
import com.brilworks.accounts.repository.PostRepository;
import com.brilworks.accounts.services.ImageDownloadUtil;
import com.brilworks.accounts.services.ImageDownloadUtilService;
import com.brilworks.accounts.services.PostService;
import com.brilworks.accounts.utils.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ImageDownloadUtilService imageDownloadUtilService;
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
                postDetail=postRepository.save(postDetail);
                imageDownloadUtilService.downloadImage(postDetail.getId(),postDetail.getImageURL());
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
            postDetail=postRepository.save(postDetail);
            imageDownloadUtilService.downloadImage(postDetail.getId(),postDetail.getImageURL());
        }
        return new ResponseDto(Constants.SUCCESS, Constants.UPDATED);
    }

    @Override
    public List<PostDetails> getAllPostByUser(Long userId) {
        return postRepository.getAllPostByUser(userId);
    }



    @Override
    public void downloadImage(String imageUrl, String destinationPath) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(imageUrl);

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity imageEntity = response.getEntity();

        if (imageEntity != null) {
            File destinationFile = new File(destinationPath);
            FileUtils.copyInputStreamToFile(imageEntity.getContent(), destinationFile);
            }
        }

    @Override
    public void saveImageName(Long postId, String imageName) {
        Optional<PostDetails> postDetails = postRepository.findById(postId);
        if (postDetails.isPresent())
        {
            postDetails.get().setImageName(imageName);
            postRepository.save(postDetails.get());
        }
    }

}
