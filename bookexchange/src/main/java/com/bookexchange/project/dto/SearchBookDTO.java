package com.bookexchange.project.dto;

public class SearchBookDTO {
    private Long bookId;
    private String title;
    private String author;
    private String genre;
    private String bookCondition; // e.g., new, like new, used
    private Long ownerId; // User who owns the book

    private boolean isUserRequested;

    public SearchBookDTO(Long bookId, String title, String author, String genre, String bookCondition, Long ownerId, boolean isUserRequested) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.bookCondition = bookCondition;
        this.ownerId = ownerId;
        this.isUserRequested = isUserRequested;
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

    public void setBookCondition(String bookCondition) {
        this.bookCondition = bookCondition;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isUserRequested() {
        return isUserRequested;
    }

    public void setUserRequested(boolean userRequested) {
        isUserRequested = userRequested;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
