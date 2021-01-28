package com.pa.shoploc.service;

import com.pa.shoploc.bo.Commande;
import com.pa.shoploc.dto.commande.CommandeDTO;
import com.pa.shoploc.enumeration.CommandeEtat;
import com.pa.shoploc.exceptions.find.CommandeNotFoundException;
import com.pa.shoploc.exceptions.find.CommercantNotFoundException;
import com.pa.shoploc.mapper.ContenuCommandeDTO;

import java.util.List;

public interface CommandeService {

    Commande creerCommande(String username,String commercant) throws Exception;
    Commande changerEtat(CommandeEtat etat,Commande c);
    Commande findById(int cid) throws CommandeNotFoundException;
    List<Commande> finAllByClient(String username) throws Exception;

    void deleteCommande(int cid) throws Exception;

    Commande addProduct(int cid, int pid, int quantite) throws Exception;

    ContenuCommandeDTO viewContentCommande(int cid) throws Exception;

    Commande confirmCommande(int cid) throws CommandeNotFoundException;

    CommandeDTO findByCommandeId(int cid) throws Exception;

    CommandeDTO paiementCommande(String username, int cid) throws Exception;

}
