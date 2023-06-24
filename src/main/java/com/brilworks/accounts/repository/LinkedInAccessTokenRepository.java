package com.brilworks.accounts.repository;

import com.brilworks.accounts.entity.LinkedInAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkedInAccessTokenRepository extends JpaRepository<LinkedInAccessToken, Long> {
    LinkedInAccessToken findByUserId(Long userId);
}
