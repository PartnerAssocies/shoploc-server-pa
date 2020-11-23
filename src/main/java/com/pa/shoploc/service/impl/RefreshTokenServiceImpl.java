package com.pa.shoploc.service.impl;

import com.pa.shoploc.bo.RefreshToken;
import com.pa.shoploc.repository.RefreshTokenRepository;
import com.pa.shoploc.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken findById(String id) {
       return refreshTokenRepository.findById(id).orElse(null);
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    @Autowired
    public void setRefreshTokenRepository(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }
}
