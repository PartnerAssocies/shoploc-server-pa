package com.pa.shoploc.controller;

import com.pa.shoploc.bo.Commande;
import com.pa.shoploc.dto.commande.CommandeDTO;
import com.pa.shoploc.exceptions.find.CommandeNotFoundException;
import com.pa.shoploc.mapper.ContenuCommandeDTO;
import com.pa.shoploc.service.CommandeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/commande")
public class CommandeController {
    private CommandeService commandeService;

    @PostMapping("/create/{client}/{commercant}")
    public Commande creerCommande(@PathVariable(value = "client")  String client
            ,@PathVariable(value = "commercant")  String commercant) throws Exception {

        return commandeService.creerCommande(client,commercant);

    }

    @PostMapping("/confirmCommande/{cid}")
    public Commande confirmCommande(@PathVariable("cid") int cid) throws CommandeNotFoundException {
        return commandeService.confirmCommande(cid);

    }

    @GetMapping("/findAllUserCommande/{username}")
    public List<Commande> findAllUserCommande(@PathVariable("username") String username) throws Exception {
        return commandeService.finAllByClient(username);
    }

    @GetMapping("/{cid}")
    public CommandeDTO findCommande(@PathVariable int cid) throws Exception {
        return commandeService.findByCommandeId(cid);
    }

    @DeleteMapping("/deleteCommande/{cid}")
    public void deleteCommande(@PathVariable("cid")int cid) throws Exception {
        commandeService.deleteCommande(cid);
    }

    @PostMapping("/{cid}/addProduct/{pid}/{quantite}")
    public Commande addProduct(@PathVariable("cid")int cid
            ,@PathVariable("pid")int pid,@PathVariable("quantite")int quantite) throws Exception {
        if(quantite<0){
            throw new IllegalArgumentException("quantite can't be inferior than 0");
        }
        return commandeService.addProduct(cid,pid,quantite);
    }


    @GetMapping("/{cid}/viewContentCommande")
    public ContenuCommandeDTO contenuCommande(@PathVariable("cid") int cid) throws Exception {
        return commandeService.viewContentCommande(cid);
    }


    @Autowired
    public void setCommandeService(CommandeService commandeService) {
        this.commandeService = commandeService;
    }
}
