package com.pa.shoploc.controller;

import com.pa.shoploc.bo.Produit;
import com.pa.shoploc.dto.produit.ProduitDTO;
import com.pa.shoploc.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/produit")
public class ProduitController {

    private ProduitService produitService;

    @PostMapping("/addProduit")
    public Produit addProduit(@RequestBody ProduitDTO produitDTO){
        return this.produitService.addProduit(produitDTO);
    }

    @PostMapping("/addStock")
    public Produit addStock(@RequestBody AddStockDTO addStockDTO){
        return this.produitService.addStock(addStockDTO.getPid(), addStockDTO.getStock());
    }

    @DeleteMapping("/deleteProduit/{pid}")
    public boolean deleteProduit(@PathVariable("pid") int pid){
        return this.produitService.deleteProduit(pid);
    }

    @GetMapping("/listProduitsFromCommercant")
    public List<Produit> listAllProduitFromCommercant(@RequestParam String cid){
        return this.produitService.listAllProduitFromCommercant(cid);
    }

    @Autowired
    public void setProduitService(ProduitService produitService) {
        this.produitService = produitService;
    }

}
