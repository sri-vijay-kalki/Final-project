package com.bookexchange.project.dto;

public class UserDTO {

    private String userName;

    private Long userId;
    private String token;

    public UserDTO(String username,Long userId, String token) {
        this.userName = username;
        this.userId = userId;
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
