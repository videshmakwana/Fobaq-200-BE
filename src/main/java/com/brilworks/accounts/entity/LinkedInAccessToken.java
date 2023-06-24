package com.brilworks.accounts.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "linkedin_access_token")
@Entity
public class LinkedInAccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "access_token",unique = true,nullable = false,columnDefinition = "MEDIUMTEXT")
    private String accessToken;
    @Column(name="expires_in")
    private Long expiresIn;
    @Column(name="scope")
    private String scope;
    @Column(name="user_urn")
    private String userUrn;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="user_response",columnDefinition = "MEDIUMTEXT")
    private String userResponse;

}
