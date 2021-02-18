package com.pa.shoploc.service.impl;

import com.pa.shoploc.bo.*;
import com.pa.shoploc.dto.commande.CommandeDTO;
import com.pa.shoploc.enumeration.CommandeEtat;
import com.pa.shoploc.enumeration.Role;
import com.pa.shoploc.exceptions.find.CommandeContientNotFoundException;
import com.pa.shoploc.exceptions.find.CommandeNotFoundException;
import com.pa.shoploc.exceptions.find.CommercantNotFoundException;
import com.pa.shoploc.exceptions.unauthorized.NoMoreStockException;
import com.pa.shoploc.exceptions.unauthorized.NotTheOwnerCommandeException;
import com.pa.shoploc.exceptions.unauthorized.UnauthorizedToDeleteCommandeException;
import com.pa.shoploc.dto.commande.ContenuCommandeDTO;
import com.pa.shoploc.repository.CommandeRepository;
import com.pa.shoploc.repository.ContientRepository;
import com.pa.shoploc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class CommandeServiceImpl implements CommandeService, DtoService<Commande,CommandeDTO> {

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
    public CommandeDTO creerCommande(String username, String commercant) throws Exception {
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
        return toDTO(c);
    }

    /**
     * Change l'etat de la commande
     *
     * @param etat
     * @param c
     * @return
     */
    @Override
    public void changerEtat(CommandeEtat etat, Commande c) {
        c.setEtat(etat);
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
    public List<CommandeDTO> finAllByClient(String username) throws Exception {
        Client c = clientService.findById(username);
        List<Commande> list=commandeRepository.findAllByClient(c);
        return toDTOList(list);
    }

    @Override
    public void deleteCommande(int cid) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        Commande commande = findById(cid);
        CommandeEtat etat = commande.getEtat();

        if (commande.getClient().getUsername().equals(username)) {
            if (etat.equals(CommandeEtat.PANNIER) || etat.equals(CommandeEtat.EN_ATTENTE_DE_PAIEMENT_DIRECT)||
                    etat.equals(CommandeEtat.EN_ATTENTE_DE_PAIEMENT_SHOPLOC))
                commandeRepository.deleteById(cid);
            else
                throw new UnauthorizedToDeleteCommandeException();
        } else {
            throw new NotTheOwnerCommandeException();
        }

    }

    @Override
    public CommandeDTO addProduct(int cid, int pid, int quantite) throws Exception {
        Commande commande = findById(cid);
        Produit produit = produitService.getProduitById(pid);
        if (produit.getStock() < quantite)
            throw new NoMoreStockException();

        //on va verif que le contient existe pas déjà
        ContientPk pk=new ContientPk();
        pk.setCid(cid);
        pk.setPid(pid);


        Contient contient = contientRepository.findById(pk).orElse(null);

        //on supprime la ligne de produit
        if(quantite==0){
            deleteContient(contient);
        }else{
            if(contient==null) {
                contient = new Contient();
                contient.setPid(produit);
                contient.setCid(commande);
            }

            //on update la quantite non en fidelite du produit
            contient.setNbProduitsNormaux(quantite);
            //on update la quantite total du produit
            updateTotalQuantiteProduit(contient);

            contientRepository.save(contient);


        }

        //on mets à jour la date de mise à jour de la commande
        updateDate(commande);

        //on mets à jour le total dela  commande à payer
        updateTotal(commande);

        commandeRepository.save(commande);

        return toDTO(commande);
    }



    private void deleteContient(Contient contient){
        if(contient!=null)
            contientRepository.delete(contient);
    }

    private void updateTotalQuantiteProduit(Contient contient) {
        contient.setQuantite(contient.getNbProduitsEnFidelite()+contient.getNbProduitsNormaux());
    }

    /**
     * On update le total à payer de la commande
     * @param commande
     */
    private void updateTotal(Commande commande) {
        double somme=0;
        for(Contient c:commande.getContenu()){
            double prix=c.getPid().getPrix();
            double quantite=c.getNbProduitsNormaux();

            somme+=prix*quantite;
        }
        commande.setTotal(somme);
    }

    /**
     * On update le total de points de fidelite à depenser
     * @param commande
     */
    private void updateTotalFidelite(Commande commande) {
        int somme=0;
        for(Contient c:commande.getContenu()){
            int fidelitePointsRequis=c.getPid().getFidelitePointsRequis();
            int quantite=c.getNbProduitsEnFidelite();

            somme+=fidelitePointsRequis*quantite;
        }
        commande.setTotalPointsFidelite(somme);
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
            p.setNbProduitsEnFidelite(c.getNbProduitsEnFidelite());
            p.setNbProduitsNormaux(c.getNbProduitsNormaux());
            produits.add(p);
        }
        contenu.setProduits(produits);
        return contenu;
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

        //la commande est paye on mets à jour les stocks de produits
        updateStockProduits(commande);


        //on va generer les points de fidélités
        int pointsFidelite=(int)(total*FACTEUR_POINTS_FIDELITES);
        client.setPointsFidelites(client.getPointsFidelites()+pointsFidelite);


        //on update les models
        clientService.update(client);
        commandeRepository.save(commande);


        return toDTO(commande);
    }

    /**
     * on mets à jour les stocks de produits
     * @param commande
     * @throws Exception
     */
    private void updateStockProduits(Commande commande) throws Exception {
        for(Contient c:commande.getContenu()){
            Produit p=produitService.getProduitById(c.getPid().getPid());
            p.setStock(p.getStock()-c.getQuantite());
            produitService.save(p);
        }

    }

    @Override
    public List<CommandeDTO> findCommandesByEtatAndCommercant(String username, CommandeEtat etat) throws CommercantNotFoundException {
        Commercant commercant=commercantService.findCommercantById(username);
        List<Commande> list=commandeRepository.findAllByCommercantAndEtatOrderByDate(commercant,etat);

        return toDTOList(list);
    }

    @Override
    public CommandeDTO nextEtatCommande(int cid, CommandeEtat etat) throws Exception {
        Commande commande=findById(cid);

        if(etat.equals(CommandeEtat.EN_ATTENTE_DE_PAIEMENT_DIRECT)
                ||etat.equals(CommandeEtat.EN_ATTENTE_DE_PAIEMENT_SHOPLOC)){
            if(commande.getContenu()==null ||commande.getContenu().isEmpty())
                throw new CommandeContientNotFoundException();
        }

        if(etat.equals(CommandeEtat.A_RECUPERER)&&
                commande.getEtat().equals(CommandeEtat.EN_ATTENTE_DE_PAIEMENT_DIRECT)){
            Client client=commande.getClient();

            //on va generer les points de fidélités
            int pointsFidelite=(int)(commande.getTotal()*FACTEUR_POINTS_FIDELITES);
            client.setPointsFidelites(client.getPointsFidelites()+pointsFidelite);

            clientService.update(client);

        }



        changerEtat(etat,commande);


        return toDTO(commandeRepository.save(commande));
    }

    @Override
    public CommandeDTO addProductFidelite(int cid, int pid, int quantite) throws Exception {
        Commande commande = findById(cid);
        Produit produit = produitService.getProduitById(pid);

        if (produit.getStock() < quantite)
            throw new NoMoreStockException();

        //on va verif que le contient existe pas déjà
        ContientPk pk=new ContientPk();
        pk.setCid(cid);
        pk.setPid(pid);

        Contient contient = contientRepository.findById(pk).orElse(new Contient(produit,commande));


        contient.setNbProduitsEnFidelite(quantite);

        //la quantite total du produit a augmente
        updateTotalQuantiteProduit(contient);

        contientRepository.save(contient);

        //on mets à jour la date de mise à jour de la commande
        updateDate(commande);

        //on mets à jour le total de points de fidelite
        updateTotalFidelite(commande);



        commandeRepository.save(commande);

        return toDTO(commande);
    }


    public CommandeDTO toDTO(Commande c){
        CommandeDTO dto=new CommandeDTO();
        dto.setCid(c.getCid());
        dto.setClient(c.getClient().getUsername());
        dto.setCommercant(c.getCommercant().getUsername());
        dto.setCreeParClickAndCollect(c.isCreeParClickAndCollect());
        dto.setDate(c.getDate());
        dto.setEtat(c.getEtat());
        dto.setNote(c.getNote());
        dto.setTotal(c.getTotal());
        dto.setTotalPointsFidelite(c.getTotalPointsFidelite());

        return dto;
    }

    @Override
    public List<CommandeDTO> toDTOList(List<Commande> list) {
        List<CommandeDTO> dtoList=new ArrayList<>();
        for(Commande d:list){
            dtoList.add(toDTO(d));
        }
        return dtoList;
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
