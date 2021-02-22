package com.pa.shoploc.repository;

import com.pa.shoploc.bo.Client;
import com.pa.shoploc.bo.Commande;
import com.pa.shoploc.bo.Commercant;
import com.pa.shoploc.enumeration.CommandeEtat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande,Integer> {

    List<Commande> findAllByClient(Client client);
    List<Commande> findAllByCommercantAndEtatOrderByDate(Commercant commercant, CommandeEtat etat);
    @Query("SELECT function('date_format',c1.date, '%y-%m-%D') FROM Commande c1 where c1.etat " +
            "in (:etats) and c1.date >= :dayPointCheck " +
            "AND c1.client=:c group by function('date_format',c1.date, '%y-%m-%D')")
    List<String> findCommandFromOneWeekByUsername(Client c, Date dayPointCheck,List<CommandeEtat> etats);
}
