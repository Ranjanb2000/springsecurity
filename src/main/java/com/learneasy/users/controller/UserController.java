package com.learneasy.users.controller;

import com.learneasy.users.model.User;
import com.learneasy.users.service.IusersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IusersService userService;

    @Autowired
    public UserController(IusersService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        User tempuser=userService.save(user);
        return new ResponseEntity<>(tempuser, HttpStatus.CREATED);
    }

    @GetMapping("/fetchAll")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/fetch")
    public ResponseEntity<User> getUserById(@RequestParam String userKey) {
        return userService.findByUserKey(userKey)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.findById(id)
                .map(user -> {
                    if (userDetails.getName() != null) {
                        user.setName(userDetails.getName());
                    }
                    if (userDetails.getEmail() != null) {
                        user.setEmail(userDetails.getEmail());
                    }
                    User updatedUser = userService.save(user);
                    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
