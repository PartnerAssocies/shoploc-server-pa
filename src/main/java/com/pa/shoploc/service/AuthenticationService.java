package com.pa.shoploc.service;

import com.pa.shoploc.dto.LoginDTO;
import com.pa.shoploc.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AuthenticationService extends UserDetailsService{

     LoginDTO login(String username);

     String refreshAccessToken(String token) throws Exception;

     void setJwtTokenService(JwtTokenService jwtTokenService);

     void setUserRepository(UserRepository userRepository);

    void revoke(String token);
}
