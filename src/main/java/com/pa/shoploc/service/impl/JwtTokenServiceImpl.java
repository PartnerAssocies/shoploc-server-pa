package com.pa.shoploc.service.impl;

import com.pa.shoploc.bo.RefreshToken;
import com.pa.shoploc.service.JwtTokenService;
import com.pa.shoploc.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtTokenServiceImpl implements JwtTokenService{

    private static final String SECRETKEY="secret";
    private RefreshTokenService refreshTokenService;

    /**
     * Genere le refresh token et l'insere en bdd
     * @param username
     * @return
     */
    @Override
    public RefreshToken generateRefreshToken(String username) {
        String uuid= UUID.randomUUID().toString();
        RefreshToken refreshToken=new RefreshToken();
        refreshToken.setRefreshToken(uuid);
        refreshToken.setUsername(username);

        return refreshTokenService.save(refreshToken);
    }

    /**
     * Genere l'access token
     * @param username
     * @return
     */
    @Override
    public String generateAccessToken(String username) {
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,username);
    }


    /**
     * Récupère le username correspondant au token passé
     * @param token
     * @return
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Récupère la date d'expiration lié au token passé
     * @param token
     * @return
     */
    public Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }


    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
    }

    /**
     * Vérifie si le token est valide
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    /**
     * Crée un token grâce au username
     * @param claims
     * @param username
     * @return
     */
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder().setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*15))
                .signWith(SignatureAlgorithm.HS256, SECRETKEY).compact();
    }

    /**
     * Valide un tocken grâce au détail de user
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateAccessToken(String token,UserDetails userDetails) {
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername())&&!isTokenExpired(token));
    }


    @Autowired
    public void setRefreshTokenService(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }
}
