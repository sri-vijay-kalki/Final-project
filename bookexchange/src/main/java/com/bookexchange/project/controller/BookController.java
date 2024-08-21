package com.bookexchange.project.controller;

import com.bookexchange.project.dto.SearchBookDTO;
import com.bookexchange.project.exception.UnAuthorizedException;
import com.bookexchange.project.model.User;
import com.bookexchange.project.service.UserService;
import com.bookexchange.project.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookexchange.project.model.Book;
import com.bookexchange.project.service.BookService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/books")
public class BookController {
   @Autowired
   private BookService bookService;

   @Autowired
   private UserService userService;

   @PostMapping
   public ResponseEntity<Book> addBook(
           @RequestBody @Valid Book book,
           @RequestHeader(value = "Authorization", required = true) String authorizationHeader) throws Exception{
      Pair<String, String> creds = CommonUtil.getuserCredsFromToken(authorizationHeader);
      User user  = userService.findUser(creds.getFirst(), creds.getSecond());
      if (user == null || !book.getOwnerId().equals(user.getId())) {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }
      book.setOwnerId(user.getId());
      Book savedBook = bookService.saveBook(book);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
   }

   @GetMapping
   public ResponseEntity<List<Book>> getMyPostings(@RequestHeader(value = "Authorization", required = true) String authorizationHeader) throws UnAuthorizedException{
      Pair<String, String> creds = CommonUtil.getuserCredsFromToken(authorizationHeader);
      User user  = userService.findUser(creds.getFirst(), creds.getSecond());
      if (user == null) {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }
      return ResponseEntity.status(HttpStatus.OK).body(bookService.getMyBooks(user.getId()));
   }

@GetMapping("/search")
public List<SearchBookDTO> searchBooks(@RequestParam(required = false) String title,
                                       @RequestParam(required = false) String author,
                                       @RequestParam(required = false) String genre, HttpServletRequest request) throws UnAuthorizedException {
   Pair<String,String> creds = CommonUtil.getUser(request);
   User user = userService.findUser(creds.getFirst(), creds.getSecond());
   if(user == null)
      throw new UnAuthorizedException();
   return bookService.searchBooks(title, author, genre,user.getId());
}
   @PutMapping("/{id}")
   public ResponseEntity<Book> updateBook(
           @PathVariable Long id,
           @RequestBody @Valid Book updatedBook,
           @RequestHeader(value = "Authorization", required = true) String authorizationHeader) throws Exception {

      Pair<String, String> creds = CommonUtil.getuserCredsFromToken(authorizationHeader);
      User user  = userService.findUser(creds.getFirst(), creds.getSecond());

      if (user == null || !updatedBook.getOwnerId().equals(user.getId())) {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }

      // Retrieve the existing book
      Book existingBook = bookService.getBookById(id);
      if (existingBook == null) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }

      // Update the existing book with new details
      existingBook.setTitle(updatedBook.getTitle());
      existingBook.setAuthor(updatedBook.getAuthor());
      existingBook.setGenre(updatedBook.getGenre());
      existingBook.setBookCondition(updatedBook.getBookCondition());
      // Update other fields as necessary

      // Save the updated book
      Book savedBook = bookService.updateBook(existingBook);
      return ResponseEntity.status(HttpStatus.OK).body(savedBook);
   }

   @GetMapping("/{id}")
   public ResponseEntity<Book> getBookById(@PathVariable Long id) {
      Book book = bookService.getBookById(id);
      if (book != null) {
         return ResponseEntity.ok(book);
      } else {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }
   }



}
