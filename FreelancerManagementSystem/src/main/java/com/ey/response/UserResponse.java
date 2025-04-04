package com.ey.response;

import java.time.LocalDateTime;

import com.ey.model.Role;

public class UserResponse {
	
    private Long userId;
    private String username;
    private String email;
    private Role role;
    private String userStatus; // Add user status field
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserResponse() {
        super();
    }

    public UserResponse(Long userId, String username, String email, Role role, String userStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super();
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.userStatus = userStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public String getUserStatus() { // Getter for user status
        return userStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
