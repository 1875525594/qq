package com.gdm.QQCommon;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;//用户ID
    private String userwsd;//用户密码

    public User() {
    }

    public User(String userId, String userwsd) {
        this.userId = userId;
        this.userwsd = userwsd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserwsd() {
        return userwsd;
    }

    public void setUserwsd(String userwsd) {
        this.userwsd = userwsd;
    }
}
