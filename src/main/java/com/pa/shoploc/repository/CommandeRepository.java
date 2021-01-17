package com.pa.shoploc.repository;

import com.pa.shoploc.bo.Client;
import com.pa.shoploc.bo.Commande;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends JpaRepository<Commande,Integer> {

    List<Commande> findAllByClient(Client client);
}
