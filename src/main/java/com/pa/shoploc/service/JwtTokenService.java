package com.pa.shoploc.service;

import com.pa.shoploc.bo.RefreshToken;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {

    RefreshToken generateRefreshToken(String username);
    String generateAccessToken(String username);
    String extractUsername(String token);
    boolean validateAccessToken(String token, UserDetails userDetails);

}
