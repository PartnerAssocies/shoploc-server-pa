package com.pa.shoploc.mapper;

import com.pa.shoploc.bo.Produit;
import com.pa.shoploc.dto.produit.ProduitDTO;

public class ProduitMapper {

    private ProduitMapper(){

    }

    public static Produit produitDTOToProduit(ProduitDTO produitDTO){
        Produit p = new Produit();
        p.setFidelitePointsRequis(produitDTO.getFidelitePointsRequis());
        p.setImage(produitDTO.getImage());
        p.setLibelle(produitDTO.getLibelle());
        p.setPrix(produitDTO.getPrix());
        p.setStock(produitDTO.getStock());
        return p;
    }
}
