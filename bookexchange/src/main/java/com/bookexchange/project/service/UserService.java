package com.bookexchange.project.service;

import com.bookexchange.project.dto.AdminUserDisplayDTO;
import com.bookexchange.project.dto.UserDTO;
import com.bookexchange.project.exception.ConflictException;
import com.bookexchange.project.exception.UnAuthorizedException;
import com.bookexchange.project.model.Book;
import com.bookexchange.project.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookexchange.project.model.User;
import com.bookexchange.project.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Or inject this

    public User register(User user) throws Exception {
        User inpUser = userRepository.findByUsername(user.getUsername());
        if (inpUser != null) {
            throw new ConflictException();
        }
        // Encrypt the user's password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public UserDTO login(User inputUser) throws UnAuthorizedException {
        // Find user by username
        User user = userRepository.findByUsername(inputUser.getUsername());
        if (user == null) {
            throw new UnAuthorizedException();
        }
        // Check if the provided password matches the stored password
        if (passwordEncoder.matches(inputUser.getPassword(), user.getPassword()) && inputUser.getRole().equals(user.getRole())) {
            return new UserDTO(inputUser.getUsername(),user.getId(), CommonUtil.getToken(inputUser.getUsername(), inputUser.getPassword()));
        }else{
            throw new UnAuthorizedException();
        }

    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findUser(String userName, String password){
        User user = userRepository.findByUsername(userName);
        if (user != null) {
            // Check if the provided password matches the stored password
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public List<AdminUserDisplayDTO> getAllUsers() {
        return userRepository.findAllWithBookCount();
    }
}
