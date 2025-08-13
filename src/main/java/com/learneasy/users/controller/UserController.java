package com.learneasy.users.controller;

import com.learneasy.users.model.MyUser;
import com.learneasy.users.service.IusersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IusersService userService;
    private final PasswordEncoder passwordEncoder;



    @PostMapping("/create")
    public ResponseEntity<MyUser> createUser(@RequestBody MyUser myUser)
    {
        String hashPwd = passwordEncoder.encode(myUser.getPassword());
        myUser.setPassword(hashPwd);
        MyUser tempuser=userService.save(myUser);
        return new ResponseEntity<>(tempuser, HttpStatus.CREATED);
    }

    @GetMapping("/fetchAll")
    public List<MyUser> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<MyUser> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(myUser -> new ResponseEntity<>(myUser, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/fetch")
    public ResponseEntity<MyUser> getUserById(@RequestParam String userKey) {
        return userService.findByUserKey(userKey)
                .map(myUser -> new ResponseEntity<>(myUser, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<MyUser> patchUser(@PathVariable Long id, @RequestBody MyUser myUserDetails) {
        return userService.findById(id)
                .map(myUser -> {
                    if (myUserDetails.getName() != null) {
                        myUser.setName(myUserDetails.getName());
                    }
                    if (myUserDetails.getEmail() != null) {
                        myUser.setEmail(myUserDetails.getEmail());
                    }
                    MyUser updatedMyUser = userService.save(myUser);
                    return new ResponseEntity<>(updatedMyUser, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
