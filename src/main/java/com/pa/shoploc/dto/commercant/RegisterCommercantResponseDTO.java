package com.pa.shoploc.dto.commercant;

public class RegisterCommercantResponseDTO {

    private String username;

    public String getUsername() {
        return username;
    }

    public RegisterCommercantResponseDTO(String username){
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
