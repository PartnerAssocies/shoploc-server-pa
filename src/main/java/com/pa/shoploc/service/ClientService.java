package com.pa.shoploc.service;

import com.pa.shoploc.bo.Client;
import com.pa.shoploc.bo.PaiementHisto;
import com.pa.shoploc.dto.client.VfpDTO;
import com.pa.shoploc.dto.register.RegisterClientRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {

     Client register(RegisterClientRequestDTO user) throws Exception;

     Client findById(String username) throws Exception;

     Client changeMoney(String username, double money) throws Exception;

     List<PaiementHisto> findAllPaiementHisto(String username) throws Exception;

     Client update(Client client) throws Exception;

     Double retrieveArgent(String username) throws Exception;

     int retrieveFidelite(String username) throws Exception;

     VfpDTO vfpStatus(String username) throws Exception;
}
