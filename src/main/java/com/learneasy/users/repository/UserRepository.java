package com.learneasy.users.repository;

import com.learneasy.users.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {


    Optional<MyUser> findByName(String name);

    Optional<MyUser> findByUserKey(String userKey);

    // Custom query to check if a unique key already exists
    boolean existsByUserKey(String userKey);
}
