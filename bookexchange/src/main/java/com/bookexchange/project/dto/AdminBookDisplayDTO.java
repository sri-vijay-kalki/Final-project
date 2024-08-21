package com.bookexchange.project.dto;

public class AdminBookDisplayDTO {

    private Long id;
    private String title;
    private  String author;
    private String bookCondition;

    private Long ownerId;

    private String ownerName;

    public AdminBookDisplayDTO(Long id, String title, String author, String bookCondition, Long ownerId, String ownerName) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.bookCondition = bookCondition;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
    }

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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
