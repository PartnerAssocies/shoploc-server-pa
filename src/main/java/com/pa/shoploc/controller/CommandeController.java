package com.pa.shoploc.controller;

import com.pa.shoploc.dto.commande.CommandeDTO;
import com.pa.shoploc.enumeration.CommandeEtat;
import com.pa.shoploc.exceptions.find.CommandeNotFoundException;
import com.pa.shoploc.dto.commande.ContenuCommandeDTO;
import com.pa.shoploc.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/commande")
public class CommandeController {
    private CommandeService commandeService;

    @PostMapping("/create/{client}/{commercant}")
    public CommandeDTO creerCommande(@PathVariable(value = "client")  String client
            ,@PathVariable(value = "commercant")  String commercant) throws Exception {

        return commandeService.creerCommande(client,commercant);

    }

    @PostMapping("/confirmCommande/{cid}")
    public CommandeDTO confirmCommande(@PathVariable("cid") int cid) throws CommandeNotFoundException {
        return commandeService.nextEtatCommande(cid,CommandeEtat.EN_ATTENTE_DE_PAIEMENT);
    }

    @PostMapping("/aRecuperer/{cid}")
    public CommandeDTO aRecuperer(@PathVariable("cid") int cid) throws CommandeNotFoundException {
        return commandeService.nextEtatCommande(cid,CommandeEtat.A_RECUPERER);
    }

    @PostMapping("/recupere/{cid}")
    public CommandeDTO recupere(@PathVariable("cid") int cid) throws CommandeNotFoundException {
        return commandeService.nextEtatCommande(cid,CommandeEtat.RECUPEREE);
    }

    @GetMapping("/findAllUserCommande/{username}")
    public List<CommandeDTO> findAllUserCommande(@PathVariable("username") String username) throws Exception {
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
    public CommandeDTO addProduct(@PathVariable("cid")int cid
            ,@PathVariable("pid")int pid,@PathVariable("quantite")int quantite) throws Exception {
        if(quantite<0){
            throw new IllegalArgumentException("quantite can't be inferior than 0");
        }
        return commandeService.addProduct(cid,pid,quantite);
    }


    @PostMapping("/paiementCommande/{username}/{cid}")
    public CommandeDTO paiementCommande(@PathVariable("username") String username, @PathVariable("cid") int cid) throws Exception{
        if(StringUtils.isEmpty(username))
            throw new IllegalArgumentException();

        return commandeService.paiementCommande(username,cid);
    }

    @GetMapping("/{cid}/viewContentCommande")
    public ContenuCommandeDTO contenuCommande(@PathVariable("cid") int cid) throws Exception {
        return commandeService.viewContentCommande(cid);
    }

    @GetMapping("/findCommandesByEtatAndCommercant/{username}/{etat}")
    public List<CommandeDTO> findCommandesByEtatAndCommercant(@PathVariable("username") String username
            , @PathVariable("etat") CommandeEtat etat) throws Exception {

        return commandeService.findCommandesByEtatAndCommercant(username,etat);
    }


    @Autowired
    public void setCommandeService(CommandeService commandeService) {
        this.commandeService = commandeService;
    }
}
