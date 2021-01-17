package com.pa.shoploc.repository;

import com.pa.shoploc.bo.Commercant;
import com.pa.shoploc.bo.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit,Integer> {

    List<Produit> findAllByCid(Commercant cid);
}
