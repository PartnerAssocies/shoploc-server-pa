package com.pa.shoploc.bo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RefreshToken {
    @Id
    private String refreshToken;
    private String username;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
