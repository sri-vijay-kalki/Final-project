package com.bookexchange.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Book {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @NotBlank(message = "Title cannot be empty")
   @Size(max = 100, message = "Title should not exceed 100 characters")
   private String title;

   @NotBlank(message = "Author cannot be empty")
   @Size(max = 50, message = "Author name should not exceed 50 characters")
   private String author;
   @NotBlank(message = "Genre cannot be empty")
   @Size(max = 10, message = "Genre should not exceed 10 characters")
   private String genre;
   @Pattern(regexp = "^(NEW|LIKE NEW|USED)$", message = "Book condition must be NEW, LIKE NEW, or USED")
   private String bookCondition; // e.g., new, like new, used

   @NotNull(message = "Owner ID cannot be null")
   private Long ownerId; // User who owns the book

// Getters and Setters
public Long getId() {
   return id;
}

public void setId(Long id) {
   this.id = id;
}

public String getTitle() {
   return title;
}

public void setTitle(String title) {
   this.title = title;
}

public String getAuthor() {
   return author;
}

public void setAuthor(String author) {
   this.author = author;
}

public String getGenre() {
   return genre;
}

public void setGenre(String genre) {
   this.genre = genre;
}

public String getBookCondition() {
   return bookCondition;
}

public void setBookCondition(String condition) {
   this.bookCondition = condition;
}

public Long getOwnerId() {
   return ownerId;
}

public void setOwnerId(Long ownerId) {
   this.ownerId = ownerId;
}
}
