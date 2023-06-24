package com.brilworks.accounts.repository;

import com.brilworks.accounts.entity.LinkedInState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkedInStateRepository  extends JpaRepository<LinkedInState, Long> {
    LinkedInState findByUserId(Long userId);
    LinkedInState findByState(String state);
}
