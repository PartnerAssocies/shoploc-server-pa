package com.pa.shoploc.controller;

import com.pa.shoploc.dto.LoginDTO;
import com.pa.shoploc.exceptions.token.InvalidRefreshTokenException;
import com.pa.shoploc.service.AuthenticationService;
import com.pa.shoploc.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Controller de gestion de l'authentification
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private AuthenticationService authenticationService;
    /**
     * methode principale d'authentification d'un user
     * @param username
     * @param password
     * @return un user avec son access-token et son refresh-token
     */
    @PostMapping("/login")
    public LoginDTO login(@RequestParam("username")String username,@RequestParam("password")String password){
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password))
            throw new IllegalArgumentException();

        Authentication authentication=new UsernamePasswordAuthenticationToken(username,password);

        authenticationManager.authenticate(authentication);
        LoginDTO dto=authenticationService.login(username);

        return dto;
    }

    @PostMapping("/refresh/{refresh-token}")
    public String refresh(@PathVariable(value = "refresh-token")  String token) throws Exception{
        if(StringUtils.isEmpty(token))
            throw new IllegalArgumentException();

        return authenticationService.refreshAccessToken(token);
    }

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
}
