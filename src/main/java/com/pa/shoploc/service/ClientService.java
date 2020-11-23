package com.pa.shoploc.service;

import com.pa.shoploc.bo.Client;
import com.pa.shoploc.dto.register.RegisterClientRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface ClientService {

     Client register(RegisterClientRequestDTO user) throws Exception;

     Client findById(String username) throws Exception;
}
