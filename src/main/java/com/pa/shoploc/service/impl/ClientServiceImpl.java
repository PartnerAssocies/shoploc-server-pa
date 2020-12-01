package com.pa.shoploc.service.impl;

import com.pa.shoploc.bo.Client;
import com.pa.shoploc.bo.Lieu;
import com.pa.shoploc.dto.register.RegisterClientRequestDTO;
import com.pa.shoploc.exceptions.find.EmailAlreadyExistException;
import com.pa.shoploc.exceptions.find.UserNotFoundException;
import com.pa.shoploc.exceptions.save.UnableToSaveUserException;
import com.pa.shoploc.repository.ClientRepository;
import com.pa.shoploc.service.ClientService;
import com.pa.shoploc.service.LieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Classe de service de l'entite User
 */
@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private LieuService lieuService;


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

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Autowired
    public void setLieuService(LieuService lieuService) {
        this.lieuService = lieuService;
    }
}
