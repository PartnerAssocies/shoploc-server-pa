package com.pa.shoploc.service.impl;

import com.pa.shoploc.bo.*;
import com.pa.shoploc.enumeration.CommandeEtat;
import com.pa.shoploc.enumeration.Role;
import com.pa.shoploc.exceptions.find.CommandeNotFoundException;
import com.pa.shoploc.exceptions.unauthorized.NoMoreStockException;
import com.pa.shoploc.exceptions.unauthorized.NotTheOwnerCommandeException;
import com.pa.shoploc.exceptions.unauthorized.UnauthorizedToDeleteCommandeException;
import com.pa.shoploc.mapper.ContenuCommandeDTO;
import com.pa.shoploc.repository.CommandeRepository;
import com.pa.shoploc.repository.ContientRepository;
import com.pa.shoploc.service.ClientService;
import com.pa.shoploc.service.CommandeService;
import com.pa.shoploc.service.CommercantService;
import com.pa.shoploc.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CommandeServiceImpl implements CommandeService {

    private CommandeRepository commandeRepository;
    private CommercantService commercantService;
    private ClientService clientService;
    private ProduitService produitService;
    private ContientRepository contientRepository;

    /**
     * Si le createur est l'utilisateur lui même, dans ce cas c'est une commande avec click&collect,
     * dans le cas contraire c'est une commande faite en magasin.
     *
     * @param username
     * @return
     */
    @Override
    public Commande creerCommande(String username, String commercant) throws Exception {
        Commercant com = commercantService.findCommercantById(commercant);
        Client client = clientService.findById(username);
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Commande c = new Commande();
        boolean clickAndCollectCreation = authorities.contains("ROLE_" + Role.CLIENT.toString());
        c.setCreeParClickAndCollect(clickAndCollectCreation);
        changerEtat(CommandeEtat.PANNIER, c);
        c.setClient(client);
        c.setCommercant(com);
        commandeRepository.save(c);
        return c;
    }

    /**
     * Change l'etat de la commande
     *
     * @param etat
     * @param c
     * @return
     * @throws CommandeNotFoundException
     */
    @Override
    public Commande changerEtat(CommandeEtat etat, Commande c) {
        c.setEtat(etat);
        return c;
    }


    @Override
    public Commande findById(int cid) throws CommandeNotFoundException {
        Commande c = commandeRepository.findById(cid).orElse(null);
        if (c == null)
            throw new CommandeNotFoundException();
        return c;
    }

    @Override
    public List<Commande> finAllByClient(String username) throws Exception {
        Client c = clientService.findById(username);
        return commandeRepository.findAllByClient(c);
    }

    @Override
    public void deleteCommande(int cid) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        Commande commande = findById(cid);
        CommandeEtat etat = commande.getEtat();

        if (commande.getClient().getUsername().equals(username)) {
            if (etat.equals(CommandeEtat.PANNIER) || etat.equals(CommandeEtat.EN_ATTENTE_DE_PAIEMENT))
                commandeRepository.deleteById(cid);
            else
                throw new UnauthorizedToDeleteCommandeException();
        } else {
            throw new NotTheOwnerCommandeException();
        }

    }

    @Override
    public Commande addProduct(int cid, int pid, int quantite) throws Exception {
        Commande commande = findById(cid);
        Produit produit = produitService.getProduitById(pid);
        if (produit.getStock() < quantite)
            throw new NoMoreStockException();
        Contient contient = new Contient();
        contient.setQuantite(quantite);
        contient.setPid(produit);
        contient.setCid(commande);
        contientRepository.save(contient);
        return commande;
    }

    @Override
    public ContenuCommandeDTO viewContentCommande(int cid) throws Exception {
        Commande commande = findById(cid);
        List<Contient> contient = contientRepository.findByCid(commande);
        ContenuCommandeDTO contenu = new ContenuCommandeDTO();
        contenu.setCid(cid);
        List<ContenuCommandeDTO.ProduitCommandeDTO> produits = new ArrayList<>();
        for (Contient c : contient) {
            ContenuCommandeDTO.ProduitCommandeDTO p = new ContenuCommandeDTO.ProduitCommandeDTO();
            Produit contientProduit = c.getPid();
            p.setFidelitePointsRequis(contientProduit.getFidelitePointsRequis());
            p.setImage(contientProduit.getImage());
            p.setLibelle(contientProduit.getLibelle());
            p.setPid(contientProduit.getPid());
            p.setQuantite(c.getQuantite());
            produits.add(p);
        }
        contenu.setProduits(produits);
        return contenu;
    }


    @Autowired
    public void setCommandeRepository(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setCommercantService(CommercantService commercantService) {
        this.commercantService = commercantService;
    }

    @Autowired
    public void setProduitService(ProduitService produitService) {
        this.produitService = produitService;
    }

    @Autowired
    public void setContientRepository(ContientRepository contientRepository) {
        this.contientRepository = contientRepository;
    }
}
