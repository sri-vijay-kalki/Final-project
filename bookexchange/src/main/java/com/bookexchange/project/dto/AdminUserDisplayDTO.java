package com.bookexchange.project.dto;

public class AdminUserDisplayDTO {
    private String userName;
    private Long bookPostCount;

    public AdminUserDisplayDTO(String userName, Long bookPostCount) {
        this.userName = userName;
        this.bookPostCount = bookPostCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getBookPostCount() {
        return bookPostCount;
    }

    public void setBookPostCount(Long bookPostCount) {
        this.bookPostCount = bookPostCount;
    }
}
