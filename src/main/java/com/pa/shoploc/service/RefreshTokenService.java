package com.pa.shoploc.service;

import com.pa.shoploc.bo.RefreshToken;

public interface RefreshTokenService {

    RefreshToken findById(String id);

    RefreshToken save(RefreshToken refreshToken);
}
