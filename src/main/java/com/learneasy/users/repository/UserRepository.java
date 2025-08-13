package com.learneasy.users.repository;

import com.learneasy.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByName(String name);

    Optional<User> findByUserKey(String userKey);

    // Custom query to check if a unique key already exists
    boolean existsByUserKey(String userKey);
}
