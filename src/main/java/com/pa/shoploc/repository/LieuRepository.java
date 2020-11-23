package com.pa.shoploc.repository;

import com.pa.shoploc.bo.Lieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LieuRepository extends JpaRepository<Lieu,Integer> {
}
