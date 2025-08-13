package com.learneasy.users.service;

import com.learneasy.users.model.User;

import java.util.List;
import java.util.Optional;

public interface IusersService {
    public  User save(User user);

    List<User> findAll();

    Optional<User> findById(long id);

    Optional<User> findByname(String name);

    Optional<User> findByUserKey(String userKey);
}
