package com.ey.request;

import com.ey.model.UserStatus;

public class UserStatusRequest {
    private UserStatus.Status status;

    public UserStatusRequest() {
    }

    public UserStatusRequest(UserStatus.Status status) {
        this.status = status;
    }

    public UserStatus.Status getStatus() {
        return status;
    }

    public void setStatus(UserStatus.Status status) {
        this.status = status;
    }
}
