package com.pa.shoploc.service.impl;

import com.pa.shoploc.bo.Commercant;
import com.pa.shoploc.bo.Produit;
import com.pa.shoploc.dto.produit.ProduitDTO;
import com.pa.shoploc.exceptions.find.ProduitNotFoundException;
import com.pa.shoploc.mapper.ProduitMapper;
import com.pa.shoploc.repository.CommercantRepository;
import com.pa.shoploc.repository.ProduitRepository;
import com.pa.shoploc.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitServiceImpl implements ProduitService {

    private ProduitRepository produitRepository;
    private CommercantRepository commercantRepository;

    @Override
    public Produit addProduit(ProduitDTO p) {
        Produit toReturn = ProduitMapper.produitDTOToProduit(p);
        Commercant c = this.commercantRepository.findById(p.getCommercantId()).orElse(null);
        if(c!=null){
            toReturn.setCid(c);
            this.produitRepository.save(toReturn);
            return toReturn;
        }
        return null;
    }

    @Override
    public boolean deleteProduit(int p) {
        Produit produit = this.produitRepository.findById(p).orElse(null);
        if(produit!=null){
            this.produitRepository.deleteById(p);
            return true;
        }
        return false;
    }

    @Override
    public Produit getProduitById(int pid) throws Exception{
        Produit p= this.produitRepository.findById(pid).orElse(null);

        if(p==null)
            throw new ProduitNotFoundException();

        else return p;

    }

    @Override
    public Produit addStock(int pid , int stock) {
        Produit p = this.produitRepository.findById(pid).orElse(null);
        if(p!=null){
            p.setStock(p.getStock()+stock);
            this.produitRepository.save(p);
            return p;
        }
        return null;
    }

    @Override
    public List<Produit> listAllProduitFromCommercant(String cid) {
        Commercant c = this.commercantRepository.findById(cid).orElse(null);
        return this.produitRepository.findAllByCid(c);
    }

    @Override
    public Produit save(Produit p) {
        return produitRepository.save(p);
    }

    @Autowired
    public void setProduitRepository(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @Autowired
    public void setCommercantRepository(CommercantRepository commercantRepository) {
        this.commercantRepository = commercantRepository;
    }
}
