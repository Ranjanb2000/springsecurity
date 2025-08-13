package com.learneasy.users.service;

import com.learneasy.users.model.MyUser;

import java.util.List;
import java.util.Optional;

public interface IusersService {
    public MyUser save(MyUser myUser);

    List<MyUser> findAll();

    Optional<MyUser> findById(long id);

    Optional<MyUser> findByname(String name);

    Optional<MyUser> findByUserKey(String userKey);
}
