package com.pa.shoploc.service.impl;

import com.pa.shoploc.bo.Commercant;
import com.pa.shoploc.dto.commercant.RegisterCommercantDTO;
import com.pa.shoploc.exceptions.find.LieuNotFoundException;
import com.pa.shoploc.repository.CommercantRepository;
import com.pa.shoploc.service.ClientService;
import com.pa.shoploc.service.CommercantService;
import com.pa.shoploc.service.LieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommercantServiceImpl implements CommercantService {

    private CommercantRepository commercantRepository;
    private ClientService clientService;
    private LieuService lieuService;

    @Override
    public String registerCommercant(RegisterCommercantDTO commercantDTO) throws Exception {

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
        return commercant.getUsername();
    }

    @Autowired
    public void setCommercantRepository(CommercantRepository commercantRepository) {
        this.commercantRepository = commercantRepository;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setLieuService(LieuService lieuService) {
        this.lieuService = lieuService;
    }
}
