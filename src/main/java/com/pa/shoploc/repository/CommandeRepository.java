package com.pa.shoploc.repository;

import com.pa.shoploc.bo.Client;
import com.pa.shoploc.bo.Commande;
import java.util.List;

import com.pa.shoploc.bo.Commercant;
import com.pa.shoploc.enumeration.CommandeEtat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends JpaRepository<Commande,Integer> {

    List<Commande> findAllByClient(Client client);
    List<Commande> findAllByCommercantAndEtatOrderByDate(Commercant commercant, CommandeEtat etat);
}
