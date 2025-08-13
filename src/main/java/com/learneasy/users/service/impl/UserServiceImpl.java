package com.learneasy.users.service.impl;

import com.learneasy.users.model.MyUser;
import com.learneasy.users.repository.UserRepository;
import com.learneasy.users.service.IusersService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IusersService, UserDetailsService {

    private final UserRepository userRepository;
    private static final Random random = new Random();

    @Override
    public MyUser save(MyUser myUser) {
        if (myUser.getId() == null) {
            myUser.setUserKey(generateUniqueKey());
        }
        return userRepository.save(myUser);
    }

    @Override
    public List<MyUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<MyUser> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<MyUser> findByname(String name) {
        return userRepository.findByName(name);
    }
    @Override
    public Optional<MyUser> findByUserKey(String userKey) {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser =userRepository.findByName(username).orElseThrow(() -> new
                UsernameNotFoundException("User details not found for the user: " + username));
        List<GrantedAuthority> authorities=List.of(new SimpleGrantedAuthority(myUser.getRole()));
        return new User(myUser.getName(), myUser.getPassword(),authorities);
    }
}
