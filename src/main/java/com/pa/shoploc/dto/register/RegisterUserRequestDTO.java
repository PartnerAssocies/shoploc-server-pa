package com.pa.shoploc.dto.register;

import com.pa.shoploc.enumeration.Role;

public class RegisterUserRequestDTO {
    String username;
    String password;
    Role role;
    int lieu;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getLieu() {
        return lieu;
    }

    public void setLieu(int lieu) {
        this.lieu = lieu;
    }
}
