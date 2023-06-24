package com.brilworks.accounts.dto;

import com.brilworks.accounts.controller.RecStatus;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDateTime;


@Getter
@Setter
public class PostDto {
    private Long postId;
    private String ImageURL;
    private String tags;
    private String postContent;
    private Integer height;
    private Integer width;
    private Long userId;
    private String schedulerTime;
    private boolean isFacebook;
    private boolean isInstagram;
    private boolean isLinkedIn;

}
