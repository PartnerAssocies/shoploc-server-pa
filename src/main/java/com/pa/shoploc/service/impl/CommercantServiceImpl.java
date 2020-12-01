package com.pa.shoploc.service.impl;

import com.pa.shoploc.bo.Client;
import com.pa.shoploc.bo.Commercant;
import com.pa.shoploc.bo.User;
import com.pa.shoploc.dto.commercant.RegisterCommercantDTO;
import com.pa.shoploc.dto.commercant.RegisterCommercantResponseDTO;
import com.pa.shoploc.exceptions.find.CommercantAlreadyExistException;
import com.pa.shoploc.exceptions.find.EmailAlreadyExistException;
import com.pa.shoploc.exceptions.find.LieuNotFoundException;
import com.pa.shoploc.exceptions.find.UserNotFoundException;
import com.pa.shoploc.repository.CommercantRepository;
import com.pa.shoploc.repository.UserRepository;
import com.pa.shoploc.service.ClientService;
import com.pa.shoploc.service.CommercantService;
import com.pa.shoploc.service.LieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommercantServiceImpl implements CommercantService {

    private CommercantRepository commercantRepository;
    private UserRepository userRepository;
    private LieuService lieuService;

    @Override
    public RegisterCommercantResponseDTO registerCommercant(RegisterCommercantDTO commercantDTO) throws Exception {

        try{
            Commercant c = this.commercantRepository.findById(commercantDTO.getUsername()).orElse(null);
            if(c != null)
                throw new CommercantAlreadyExistException();
        }
        catch(CommercantAlreadyExistException i){
             throw i;
        }
        try{
            User u = this.userRepository.findById(commercantDTO.getUsername()).orElse(null);
            if(u != null)
                throw new EmailAlreadyExistException();
        }catch(Exception e){
            throw e;
        }
        Commercant commercant = new Commercant();
        commercant.setUsername(commercantDTO.getUsername());
        commercant.setPassword(commercantDTO.getPassword());
        commercant.setDescription(commercantDTO.getDescription());
        commercant.setImage("");
        commercant.setRole(commercantDTO.getRole());
        try {
            commercant.setLieu(lieuService.findOneById(commercantDTO.getLieuId()));
        } catch (Exception e) {
            throw new LieuNotFoundException();
        }
        commercant.setLibelleMagasin(commercantDTO.getLibelleMagasin());
        this.commercantRepository.save(commercant);
        return new RegisterCommercantResponseDTO(commercant.getUsername());
    }

    @Autowired
    public void setCommercantRepository(CommercantRepository commercantRepository) {
        this.commercantRepository = commercantRepository;
    }


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Autowired
    public void setLieuService(LieuService lieuService) {
        this.lieuService = lieuService;
    }
}
