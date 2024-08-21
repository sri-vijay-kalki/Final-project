package com.bookexchange.project.service;



import com.bookexchange.project.dto.AdminBookDisplayDTO;
import com.bookexchange.project.dto.SearchBookDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookexchange.project.model.Book;
import com.bookexchange.project.repository.BookRepository;

import java.util.List;

@Service
public class BookService {
@Autowired
private BookRepository bookRepository;

public List<SearchBookDTO> searchBooks(String title, String author, String genre, Long userId) {
   return bookRepository.searchBooks(title, author, genre, userId);
}

public Book saveBook(Book book) throws Exception {
   return bookRepository.save(book);
}

public Book getBookById(Long bookId){
   return bookRepository.findById(bookId);
}
public List<Book> getMyBooks(Long ownerId){
   return bookRepository.findBookWithOwnerId(ownerId);
}

   public Book updateBook(Book book) throws Exception {
      return bookRepository.updateBook(book);
   }

   public List<AdminBookDisplayDTO> getAllBooksDetails(){
   return bookRepository.findAllBooksWithOwnerDetails();
   }
}

