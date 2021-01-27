package com.pa.shoploc.service.impl;

import com.pa.shoploc.bo.Client;
import com.pa.shoploc.bo.Lieu;
import com.pa.shoploc.bo.PaiementHisto;
import com.pa.shoploc.dto.register.RegisterClientRequestDTO;
import com.pa.shoploc.exceptions.find.EmailAlreadyExistException;
import com.pa.shoploc.exceptions.find.UserNotFoundException;
import com.pa.shoploc.exceptions.save.UnableToSaveUserException;
import com.pa.shoploc.exceptions.unauthorized.NotEnoughMoneyException;
import com.pa.shoploc.repository.ClientRepository;
import com.pa.shoploc.service.ClientService;
import com.pa.shoploc.service.LieuService;
import com.pa.shoploc.service.PaiementHistoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Classe de service de l'entite User
 */
@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private LieuService lieuService;
    private PaiementHistoService paiementHistoService;

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
    public Client changeMoney(String username, float money) throws Exception {
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
}
