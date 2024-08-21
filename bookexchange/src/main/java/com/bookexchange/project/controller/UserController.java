package com.bookexchange.project.controller;

import com.bookexchange.project.dto.UserDTO;
import com.bookexchange.project.exception.UnAuthorizedException;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bookexchange.project.model.User;
import com.bookexchange.project.service.UserService;

import java.security.PublicKey;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody @Valid User user) throws Exception{
        return userService.register(user);
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        return userService.findByUsername(username);
    }
    @PostMapping("/login")
    public UserDTO login(@RequestBody @Valid User user) throws Exception {
            return userService.login(user);
    }
}
