package com.learneasy.users.service.impl;

import com.learneasy.users.model.User;
import com.learneasy.users.repository.UserRepository;
import com.learneasy.users.service.IusersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements IusersService {

    private UserRepository userRepository;
    private static final Random random = new Random();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setUserKey(generateUniqueKey());
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByname(String name) {
        return userRepository.findByName(name);
    }
    @Override
    public Optional<User> findByUserKey(String userKey) {
        return userRepository.findByUserKey(userKey);
    }

    private String generateUniqueKey() {
        String key;
        do {
            // Generates a random number up to 99,999,999 and formats it to be 8 digits with leading zeros
            int randomInt = random.nextInt(100000000);
            key = String.format("%08d", randomInt);
        } while (userRepository.existsByUserKey(key)); // Loop until a unique key is found
        return key;
    }
}
