package com.pa.shoploc.service;

import com.pa.shoploc.bo.Produit;
import com.pa.shoploc.dto.produit.ProduitDTO;

import java.util.List;

public interface ProduitService {

    Produit addProduit(ProduitDTO p);
    boolean deleteProduit(int pid);
    Produit getProduitById(int pid) throws Exception;
    Produit addStock(int pid, int stock);
    List<Produit> listAllProduitFromCommercant(String cid);

    Produit save(Produit p);
}
