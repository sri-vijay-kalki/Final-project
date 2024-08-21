package com.bookexchange.project.controller;


import com.bookexchange.project.dto.ExchangeRequestDTO;
import com.bookexchange.project.exception.UnAuthorizedException;
import com.bookexchange.project.model.Book;
import com.bookexchange.project.model.User;
import com.bookexchange.project.service.BookService;
import com.bookexchange.project.service.UserService;
import com.bookexchange.project.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookexchange.project.model.ExchangeRequest;
import com.bookexchange.project.service.ExchangeRequestService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/exchange-requests")
public class ExchangeRequestController {
   @Autowired
   private ExchangeRequestService exchangeRequestService;

   @Autowired
   private UserService userService;

   @Autowired
   private BookService bookService;


   @PostMapping
   public ResponseEntity<Void> createRequest(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) throws UnAuthorizedException, BadRequestException {
        Pair<String,String> creds = CommonUtil.getUser(request);
        User user = userService.findUser(creds.getFirst(), creds.getSecond());
        if(user == null)
            throw new UnAuthorizedException();
        if(requestBody.get("bookId") == null)
            throw  new BadRequestException();
        Long bookId = Long.valueOf((Integer)requestBody.get("bookId"));
        Book book =  bookService.getBookById(bookId);
        if(book == null)
            throw new BadRequestException();
        ExchangeRequest req =  exchangeRequestService.saveRequest(user, book);
        return new ResponseEntity<>(HttpStatus.CREATED);
   }

   @GetMapping("/by-requester/{requesterId}")
   public List<ExchangeRequestDTO> getRequestsByRequester(@PathVariable Long requesterId,HttpServletRequest request) throws UnAuthorizedException{
       Pair<String,String> creds = CommonUtil.getUser(request);
       User user = userService.findUser(creds.getFirst(), creds.getSecond());
       if(user == null  || !user.getId().equals(requesterId))
           throw new UnAuthorizedException();
       return exchangeRequestService.findRequestsByRequesterId(requesterId);
   }

   @GetMapping("/by-owner/{ownerId}")
   public List<ExchangeRequestDTO> getRequestsByOwner(@PathVariable Long ownerId, HttpServletRequest request) throws UnAuthorizedException {
       Pair<String,String> creds = CommonUtil.getUser(request);
       User user = userService.findUser(creds.getFirst(), creds.getSecond());
       if(user == null  || !user.getId().equals(ownerId))
           throw new UnAuthorizedException();
      return exchangeRequestService.findRequestsByOwnerId(ownerId);
   }

    @PutMapping("/{exchageRequestId}")
    public ResponseEntity<Void> updateRequest(@PathVariable Long exchageRequestId,@RequestBody Map<String, Object> requestBody, HttpServletRequest request) throws UnAuthorizedException, BadRequestException {
        Pair<String,String> creds = CommonUtil.getUser(request);
        User user = userService.findUser(creds.getFirst(), creds.getSecond());
        if(user == null)
            throw new UnAuthorizedException();
        if(requestBody.get("status") == null)
            throw  new BadRequestException();
        String status = (String)requestBody.get("status");
        exchangeRequestService.updateRequest(exchageRequestId,user, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


