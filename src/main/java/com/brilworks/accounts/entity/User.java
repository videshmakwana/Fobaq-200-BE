package com.brilworks.accounts.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "profile_Image")
    private String profileImage;

    @Column(name = "token",columnDefinition = "text")
    private String token;

    @Column(name = "timezone")
    private Date timeZone;

    @Column(name = "is_sign_up")
    private Boolean isSignUp = false;
}
