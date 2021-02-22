package com.pa.shoploc.service.impl;

import com.pa.shoploc.bo.Client;
import com.pa.shoploc.bo.Commande;
import com.pa.shoploc.bo.Lieu;
import com.pa.shoploc.bo.PaiementHisto;
import com.pa.shoploc.dto.client.VfpDTO;
import com.pa.shoploc.dto.register.RegisterClientRequestDTO;
import com.pa.shoploc.enumeration.CommandeEtat;
import com.pa.shoploc.exceptions.find.EmailAlreadyExistException;
import com.pa.shoploc.exceptions.find.UserNotFoundException;
import com.pa.shoploc.exceptions.save.UnableToSaveUserException;
import com.pa.shoploc.exceptions.unauthorized.NotEnoughMoneyException;
import com.pa.shoploc.repository.ClientRepository;
import com.pa.shoploc.repository.CommandeRepository;
import com.pa.shoploc.service.ClientService;
import com.pa.shoploc.service.LieuService;
import com.pa.shoploc.service.PaiementHistoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * Classe de service de l'entite User
 */
@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private LieuService lieuService;
    private PaiementHistoService paiementHistoService;
    private CommandeRepository commandeRepository;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private GregorianCalendar cal = new GregorianCalendar();

    /**
     * Crée un user
     */
    @Override
    public Client register(RegisterClientRequestDTO user) throws Exception {

        try {
            findById(user.getUsername());
            throw new EmailAlreadyExistException();
        } catch (UserNotFoundException e) {
            Lieu l = lieuService.findOneById(user.getLieu());
            Client request = new Client();

            //remplie l'objet à save
            request.setUsername(user.getUsername());
            request.setNom(user.getNom());
            request.setPassword(user.getPassword());
            request.setPrenom(user.getPrenom());
            request.setRole(user.getRole());
            request.setLieu(l);
            request.setArgent(0);
            request.setPointsFidelites(0);


            Client response = clientRepository.save(request);

            if (response == null)
                throw new UnableToSaveUserException();

            return response;
        }

    }

    @Override
    public Client findById(String username) throws Exception {
        Client c = clientRepository.findById(username).orElse(null);
        if (c == null)
            throw new UserNotFoundException();

        return c;
    }

    @Override
    public Client changeMoney(String username, double money) throws Exception {
        Client c=findById(username);

        double newMoney=c.getArgent()+money;

        //on est dans le cas où on retire de l'argent, on verifie si l'utilisateur en a assez
        if(newMoney<0){
            throw new NotEnoughMoneyException();
        }else{
            c.setArgent(newMoney);
            clientRepository.save(c);
            paiementHistoService.addTransaction(money,c);
        }
        return c;
    }

    @Override
    public List<PaiementHisto> findAllPaiementHisto(String username) throws Exception {
        Client user=findById(username);

        return paiementHistoService.findUserHisto(user);
    }

    @Override
    public Client update(Client client){
        return clientRepository.save(client);
    }

    @Override
    public Double retrieveArgent(String username) throws Exception {
        return findById(username).getArgent();
    }

    @Override
    public int retrieveFidelite(String username) throws Exception {
        return findById(username).getPointsFidelites();
    }

    @Override
    public VfpDTO vfpStatus(String username) throws Exception {
        Client c=findById(username);
        VfpDTO vfpDTO=new VfpDTO();
        Date actual=new Date();
        //on verifie si le client est actuellement vfp pour eviter le recalcul
        if(c.getVfpEndDate()==null || actual.after(c.getVfpEndDate())){

            //requete pour obtenir les dates des commandes des 7 derniers jours
            cal.setTime(actual);
            cal.add(Calendar.DATE, -8);
            List<CommandeEtat> validState= Arrays.asList(CommandeEtat.EN_PREPARATION,CommandeEtat.A_RECUPERER, CommandeEtat.RECUPEREE);
            List<String> response=commandeRepository.findCommandFromOneWeekByUsername(c,cal.getTime(),validState);

            //si on a des commandes sur 5 jours minimum on est vfp
            if(response.size()>4){
                cal.setTime(actual);
                //la date limite est 1 jour après
                cal.add(Calendar.DATE,1);

                c.setDayBeforeVfp(0);
                c.setVfpEndDate(cal.getTime());
                c.setVfpBeginDate(new Date());

            }else{
                int dayBeforeVfp=5-response.size();
                c.setDayBeforeVfp(dayBeforeVfp);

            }

            clientRepository.save(c);

        }



        return vfpToDTO(c);
    }

    private VfpDTO vfpToDTO(Client c){
        VfpDTO dto=new VfpDTO();
        dto.setBeginDate(c.getVfpBeginDate());
        dto.setEndDate(c.getVfpEndDate());
        dto.setNbDaysBefore(c.getDayBeforeVfp());

        return dto;
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Autowired
    public void setLieuService(LieuService lieuService) {
        this.lieuService = lieuService;
    }

    @Autowired
    public void setPaiementHistoService(PaiementHistoService paiementHistoService) {
        this.paiementHistoService = paiementHistoService;
    }

    @Autowired
    public void setCommandeRepository(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }
}
