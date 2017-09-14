package com.example.gitusers;

/**
 * Created by Константин on 14.09.2017.
 */

public class User {

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    private String login,avatarUrl;
}
