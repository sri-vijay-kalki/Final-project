package com.bookexchange.project.service;


import com.bookexchange.project.dto.ExchangeRequestDTO;
import com.bookexchange.project.exception.UnAuthorizedException;
import com.bookexchange.project.model.Book;
import com.bookexchange.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookexchange.project.model.ExchangeRequest;
import com.bookexchange.project.repository.ExchangeRequestRepository;

import java.util.List;

@Service
public class ExchangeRequestService {
     @Autowired
     private ExchangeRequestRepository exchangeRequestRepository;

     public List<ExchangeRequestDTO> findRequestsByRequesterId(Long requesterId) {
      return exchangeRequestRepository.findByRequesterId(requesterId);
     }

     public List<ExchangeRequestDTO> findRequestsByOwnerId(Long ownerId) {
      return exchangeRequestRepository.findByOwnerId(ownerId);
     }

     public ExchangeRequest saveRequest(User user, Book book) {
      ExchangeRequest exchangeRequest =  new ExchangeRequest();
      System.out.println(exchangeRequest.getId());
      exchangeRequest.setRequesterId(user.getId());
      exchangeRequest.setBookId(book.getId());
      exchangeRequest.setOwnerId(book.getOwnerId());
      exchangeRequest.setStatus("PENDING");
      return exchangeRequestRepository.save(exchangeRequest);
     }
     public void updateRequest(Long exchageReqestId, User user, String status) throws UnAuthorizedException {
          ExchangeRequest request = exchangeRequestRepository.findById(exchageReqestId);
          if(!request.getOwnerId().equals(user.getId()))
              throw new UnAuthorizedException();
          if(status.equals("ACCEPTED"))
              exchangeRequestRepository.updateAcceptRequest(exchageReqestId,request.getBookId());
          else
              exchangeRequestRepository.updateDeclineRequest(exchageReqestId);
     }
}

