package com.bookexchange.project.controller;

import com.bookexchange.project.dto.AdminBookDisplayDTO;
import com.bookexchange.project.dto.AdminUserDisplayDTO;
import com.bookexchange.project.exception.UnAuthorizedException;
import com.bookexchange.project.model.User;
import com.bookexchange.project.model.Book;
import com.bookexchange.project.service.ExchangeRequestService;
import com.bookexchange.project.service.UserService;
import com.bookexchange.project.service.BookService;
import com.bookexchange.project.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ExchangeRequestService requestService;

    // Admin authentication check
    private void authenticateAdmin(String token) throws UnAuthorizedException {
        Pair<String, String> creds = CommonUtil.getuserCredsFromToken(token);
        User user  = userService.findUser(creds.getFirst(), creds.getSecond());
        if (user == null || !user.getRole().equals("ADMIN")) {
            throw new UnAuthorizedException("You are not authorized to access this resource");
        }
    }

    // Endpoint to get all users
    @GetMapping("/users")
    public List<AdminUserDisplayDTO> getAllUsers(@RequestHeader("Authorization") String token) throws UnAuthorizedException {
        authenticateAdmin(token);
        return userService.getAllUsers();
    }
//
    // Endpoint to get all books
    @GetMapping("/books")
    public List<AdminBookDisplayDTO> getAllBooks(@RequestHeader("Authorization") String token) throws UnAuthorizedException {
        authenticateAdmin(token);
        return bookService.getAllBooksDetails();
    }

}
