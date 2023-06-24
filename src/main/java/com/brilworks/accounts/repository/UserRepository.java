package com.brilworks.accounts.repository;

import com.brilworks.accounts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);


    @Query("SELECT user FROM User user WHERE user.email=:email")
    User getEmployeeByEmailOrUserName(@Param("email") String email);

    User findByEmail(String email);

    @Query("SELECT user FROM User user WHERE user.token=:token")
    User getUserByToken(@Param("token") String token);
}
