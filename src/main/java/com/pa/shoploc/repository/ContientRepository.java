package com.pa.shoploc.repository;

import com.pa.shoploc.bo.Commande;
import com.pa.shoploc.bo.Contient;
import com.pa.shoploc.bo.ContientPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContientRepository extends JpaRepository<Contient, ContientPk> {
    List<Contient> findByCid(Commande cid);
}
