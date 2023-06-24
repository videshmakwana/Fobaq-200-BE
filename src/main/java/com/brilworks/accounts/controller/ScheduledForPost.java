package com.brilworks.accounts.controller;

import com.brilworks.accounts.entity.PostDetails;
import com.brilworks.accounts.repository.PostRepository;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ScheduledForPost {

    @Autowired
    PostRepository  postRepository;
    @Async
    @Scheduled(initialDelay = 30000,fixedDelay = 30000)
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime futureTime = currentTime.plusMinutes(5);
        List<PostDetails> postDetails=postRepository.getAllFuturePosts(currentTime,futureTime);
        System.out.println(
                "example scheduler" + new Date());
    }
}
