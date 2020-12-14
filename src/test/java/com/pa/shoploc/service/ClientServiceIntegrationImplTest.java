package com.pa.shoploc.service;


import com.pa.shoploc.bo.Client;
import com.pa.shoploc.bo.Lieu;
import com.pa.shoploc.dto.register.RegisterClientRequestDTO;
import com.pa.shoploc.enumeration.Role;
import com.pa.shoploc.exceptions.find.UserNotFoundException;
import com.pa.shoploc.repository.ClientRepository;
import com.pa.shoploc.service.impl.ClientServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class ClientServiceIntegrationTest {

    @TestConfiguration
    static class ClientServiceTestContextConfiguration {

        @Bean
        public ClientService clientService() {
            return new ClientServiceImpl();
        }

    }

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private LieuService lieuService;

    @Before
    public void setUp() throws Exception {
        Client client = new Client();
        client.setNom("Dupont");
        client.setLieu(null);
        client.setUsername("dupont@gmail.com");
        client.setPrenom("Alex");
        client.setPointsFidelites(0);
        client.setEstVfp(false);
        client.setArgent(200);
        client.setPassword("test");
        client.setRole(Role.CLIENT);
        client.setLieu(null);

        Mockito.when(lieuService.findOneById(Mockito.anyInt())).thenReturn(null);
        Mockito.when(clientRepository.findById(client.getUsername())).thenReturn(Optional.of(client));
    }

    @Test
    public void findByIdTest() throws Exception {
        Client result=clientService.findById("dupont@gmail.com");

        assertEquals(result.getNom(),"Dupont");
    }

    @Test(expected = UserNotFoundException.class)
    public void findByIdTestUserNotFoundException() throws Exception{
        Client result=clientService.findById("existepas@gmail.com");

    }

    @Test
    public void registerTest() throws Exception{
        RegisterClientRequestDTO request=new RegisterClientRequestDTO();
        request.setNom("Dejean");
        request.setPrenom("Brian");
        request.setLieu(0);
        request.setPassword("toto");
        request.setRole(Role.CLIENT);
        request.setUsername("dejean@gmail.com");

        Client c=clientService.register(request);
        assertEquals(request.getNom(),c.getNom());

    }




}
