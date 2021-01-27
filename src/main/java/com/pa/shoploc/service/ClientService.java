package com.pa.shoploc.service;

import com.pa.shoploc.bo.Client;
import com.pa.shoploc.bo.PaiementHisto;
import com.pa.shoploc.dto.register.RegisterClientRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {

     Client register(RegisterClientRequestDTO user) throws Exception;

     Client findById(String username) throws Exception;

     Client changeMoney(String username, float money) throws Exception;

     List<PaiementHisto> findAllPaiementHisto(String username) throws Exception;
}
