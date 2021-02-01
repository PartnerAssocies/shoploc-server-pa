package com.pa.shoploc.repository;

import com.pa.shoploc.bo.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String> {
    RefreshToken findByUsername(String username);
}
