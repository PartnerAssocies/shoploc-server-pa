package com.pa.shoploc.service.impl;


import com.pa.shoploc.bo.RefreshToken;
import com.pa.shoploc.dto.LoginDTO;
import com.pa.shoploc.exceptions.token.InvalidRefreshTokenException;
import com.pa.shoploc.repository.UserRepository;
import com.pa.shoploc.service.AuthenticationService;
import com.pa.shoploc.service.JwtTokenService;
import com.pa.shoploc.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Classe de service de l'entite User
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserRepository userRepository;
    private JwtTokenService jwtTokenService;
    private RefreshTokenService refreshTokenService;

    /**
     * Methode utilise par spring security pour charge un userdetail par son nom
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails=userRepository.findById(username).orElse(null);

        if(userDetails==null)
            throw new UsernameNotFoundException("User does not exists");

        return userDetails;
    }


    /**
     * Cree un refresh-token et un access-token pour un utilisateur
     * @param username
     * @return
     */
    @Override
    public LoginDTO login(String username) {
        final UserDetails userDetails = loadUserByUsername(username);
        final String accessToken = jwtTokenService.generateAccessToken(username);
        final String role=userDetails.getAuthorities().iterator().next().getAuthority();
        final String refreshToken = jwtTokenService.generateRefreshToken(username).getRefreshToken();

        LoginDTO loginDTO=new LoginDTO();
        loginDTO.setAccessToken(accessToken);
        loginDTO.setRefreshToken(refreshToken);
        loginDTO.setRole(role);
        loginDTO.setUsername(username);

        return loginDTO;

    }

    /**
     * Cree un refresh token pour le user
     * @param token
     * @return
     */
    @Override
    public String refreshAccessToken(String token) throws Exception{
        RefreshToken refreshToken = refreshTokenService.findById(token);
        if(refreshToken==null)
            throw new InvalidRefreshTokenException();

        return jwtTokenService.generateAccessToken(refreshToken.getUsername());
    }

    @Autowired
    public void setJwtTokenService(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRefreshTokenService(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }
}
