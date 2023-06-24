package com.brilworks.accounts.entity;

import com.brilworks.accounts.controller.RecStatus;
import com.brilworks.accounts.controller.Status;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Table(name = "post_details")
@Entity
public class PostDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "image_URL")
    private String ImageURL;

    @Column(name = "tags",columnDefinition = "LONGTEXT")
    private String tags;

    @Column(name = "post_content",columnDefinition = "LONGTEXT")
    private String postContent;

    @Column(name = "height")
    private Integer height;

    @Column(name = "width")
    private Integer width;

    @OneToOne
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    @Column(name = "user_id")
    private Long userId;

    @Column(name ="scheduler_time")
    private String schedulerTime;

    @Column(name="status")
    private Status status;

    @Column(name="post_sent_time")
    private String postSentTime;

    @Column(name="isFacebook")
    private boolean isFacebook;

    @Column(name="isInstagram")
    private boolean isInstagram;

    @Column(name="isLinkedIn")
    private boolean isLinkedIn;

}
