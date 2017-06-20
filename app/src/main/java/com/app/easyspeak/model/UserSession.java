package com.app.easyspeak.model;

import java.io.Serializable;

/**
 * Created by user1 on 6/16/2017.
 */

public class UserSession {
    private String id;
    private String userName;
    private String userId;
    private Integer isActive;

    public UserSession(String id,  String userId, String userName, Integer isActive) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserSession{");
        sb.append("id='").append(id).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", isActive=").append(isActive);
        sb.append('}');
        return sb.toString();
    }
}
