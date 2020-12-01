package com.pa.shoploc.dto.commercant;

import com.pa.shoploc.enumeration.Role;

public class RegisterCommercantDTO {

    private String username;
    private String password;
    private String libelleMagasin;
    private Role role;
    private int lieuId;
    private String siret;
    private String description;

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

    public String getLibelleMagasin() {
        return libelleMagasin;
    }

    public void setLibelleMagasin(String libelleMagasin) {
        this.libelleMagasin = libelleMagasin;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getLieuId() {
        return lieuId;
    }

    public void setLieuId(int lieuId) {
        this.lieuId = lieuId;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
