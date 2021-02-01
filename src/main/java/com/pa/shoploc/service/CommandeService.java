package com.pa.shoploc.service;

import com.pa.shoploc.bo.Commande;
import com.pa.shoploc.dto.commande.CommandeDTO;
import com.pa.shoploc.enumeration.CommandeEtat;
import com.pa.shoploc.exceptions.find.CommandeNotFoundException;
import com.pa.shoploc.exceptions.find.CommercantNotFoundException;
import com.pa.shoploc.dto.commande.ContenuCommandeDTO;

import java.util.List;

public interface CommandeService {

    CommandeDTO creerCommande(String username,String commercant) throws Exception;
    void changerEtat(CommandeEtat etat,Commande c);
    Commande findById(int cid) throws CommandeNotFoundException;
    List<CommandeDTO> finAllByClient(String username) throws Exception;

    void deleteCommande(int cid) throws Exception;

    CommandeDTO addProduct(int cid, int pid, int quantite) throws Exception;

    ContenuCommandeDTO viewContentCommande(int cid) throws Exception;


    CommandeDTO findByCommandeId(int cid) throws Exception;

    CommandeDTO paiementCommande(String username, int cid) throws Exception;

    List<CommandeDTO> findCommandesByEtatAndCommercant(String username, CommandeEtat etat) throws CommercantNotFoundException;

    CommandeDTO nextEtatCommande(int cid, CommandeEtat newEtat) throws CommandeNotFoundException;
}
