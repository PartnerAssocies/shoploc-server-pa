package com.pa.shoploc.service.impl;

import com.pa.shoploc.bo.*;
import com.pa.shoploc.dto.commande.CommandeDTO;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class CommandeServiceImpl implements CommandeService {

    private CommandeRepository commandeRepository;
    private CommercantService commercantService;
    private ClientService clientService;
    private ProduitService produitService;
    private ContientRepository contientRepository;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final double FACTEUR_POINTS_FIDELITES=0.05;

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

        //on indique la date de la commande
        updateDate(c);

        commandeRepository.save(c);
        return c;
    }

    /**
     * Change l'etat de la commande
     *
     * @param etat
     * @param c
     * @return
     */
    @Override
    public Commande changerEtat(CommandeEtat etat, Commande c) {
        c.setEtat(etat);
        return c;
    }

    private void updateDate(Commande c) throws ParseException {
        Date today=new Date();
        c.setDate(sdf.parse(sdf.format(today)));
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

        //on mets à jour la date de mise à jour de la commande
        updateDate(commande);

        //on mets à jour le total de la commande à payer
        updateTotal(commande);

        commandeRepository.save(commande);

        return commande;
    }

    /**
     * On update le total à payer de la commande
     * @param commande
     */
    private void updateTotal(Commande commande) {
        double somme=0;
        for(Contient c:commande.getContenu()){
            somme+=c.getPid().getPrix()*c.getQuantite();
        }
        commande.setTotal(somme);
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
            p.setPrix(contientProduit.getPrix());
            produits.add(p);
        }
        contenu.setProduits(produits);
        return contenu;
    }

    @Override
    public Commande confirmCommande(int cid) throws CommandeNotFoundException {
        Commande commande = findById(cid);
        commande.setEtat(CommandeEtat.EN_ATTENTE_DE_PAIEMENT);

        commandeRepository.save(commande);

        return commande;
    }

    @Override
    public CommandeDTO findByCommandeId(int cid) throws Exception {
        Commande c=findById(cid);
        return toDTO(c);
    }

    @Override
    public CommandeDTO paiementCommande(String username, int cid) throws Exception {
        Commande commande=findById(cid);
        Client client=clientService.findById(username);
        double total=commande.getTotal();

        //exeption lance si pas assez d'argent
        clientService.changeMoney(username,-1*total);

        //la commande est paye elle est donc maintenant en preparation et on retire la somme a paye
        commande.setTotal(0);
        commande.setEtat(CommandeEtat.EN_PREPARATION);

        //on va generer les points de fidélités
        int pointsFidelite=(int)(total*FACTEUR_POINTS_FIDELITES);
        client.setPointsFidelites(client.getPointsFidelites()+pointsFidelite);

        //on update les models
        clientService.update(client);
        commandeRepository.save(commande);


        return toDTO(commande);
    }

    private CommandeDTO toDTO(Commande c){
        CommandeDTO dto=new CommandeDTO();
        dto.setCid(c.getCid());
        dto.setClient(c.getClient().getUsername());
        dto.setCommercant(c.getCommercant().getUsername());
        dto.setCreeParClickAndCollect(c.isCreeParClickAndCollect());
        dto.setDate(c.getDate());
        dto.setEstPayeEnFidelite(c.isCreeParClickAndCollect());
        dto.setEtat(c.getEtat());
        dto.setNote(c.getNote());
        dto.setTotal(c.getTotal());

        return dto;
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
