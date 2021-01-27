package com.pa.shoploc.repository;

import com.pa.shoploc.bo.Client;
import com.pa.shoploc.bo.PaiementHisto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaiementHistoRepository extends JpaRepository<PaiementHisto,Integer> {

    List<PaiementHisto> findByUser(Client user);

}
