package com.brilworks.accounts.repository;

import com.brilworks.accounts.entity.PostDetails;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostDetails, Long> {
    Optional<PostDetails> findById(Long id);
    @Query("SELECT pd FROM PostDetails pd WHERE pd.userId=:userId")
    List<PostDetails> getAllPostByUser(@Param("userId")Long userId);
    @Query("SELECT pd FROM PostDetails pd WHERE pd.status=CREATED AND (pd.schedulerTime BETWEEN :startTime AND :endTime)")
    List<PostDetails> getAllFuturePosts(@Param("startTime") LocalDateTime currentTime,@Param("endTime")LocalDateTime futureTime);
}
