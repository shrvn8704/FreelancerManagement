package com.ey.response;

import com.ey.model.UserStatus;

public class UserStatusResponse {
    private Long userId;
    private String status;

    public UserStatusResponse(Long userId, String status) {
        this.userId = userId;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

   
}
