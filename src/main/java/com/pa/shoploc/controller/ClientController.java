package com.pa.shoploc.controller;

import com.pa.shoploc.bo.Client;
import com.pa.shoploc.bo.PaiementHisto;
import com.pa.shoploc.dto.register.RegisterClientRequestDTO;
import com.pa.shoploc.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin("*")
@RequestMapping("/client")
public class ClientController {
    private ClientService clientService;


    /**
     * Incris un client
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Client register(@RequestBody RegisterClientRequestDTO user) throws Exception {
        if(user==null)
            throw new IllegalArgumentException();

       return clientService.register(user);
    }

    @GetMapping("/{username}")
    public Client findById(@PathVariable String username) throws Exception{
        if(StringUtils.isEmpty(username))
            throw new IllegalArgumentException();

        return clientService.findById(username);
    }

    @PostMapping("/{username}/changeMoney/{money:.+}")
    public Client changeMoney(@PathVariable("username") String username,@PathVariable("money") float money) throws Exception{
        if(StringUtils.isEmpty(username))
            throw new IllegalArgumentException();

        return clientService.changeMoney(username,money);
    }


    @PostMapping("/{username}/paiementHisto")
    public List<PaiementHisto> paiementHisto(@PathVariable("username") String username) throws Exception{
        if(StringUtils.isEmpty(username))
            throw new IllegalArgumentException();

        return clientService.findAllPaiementHisto(username);
    }

    @GetMapping("/{username}/solde")
    public Map soldeClient(@PathVariable("username") String username) throws Exception{
        if(StringUtils.isEmpty(username))
            throw new IllegalArgumentException();

        return Collections.singletonMap("solde", clientService.retrieveArgent(username));
    }

    @GetMapping("/{username}/soldeFidelite")
    public Map soldeFidelite(@PathVariable("username") String username) throws Exception{
        if(StringUtils.isEmpty(username))
            throw new IllegalArgumentException();

        return Collections.singletonMap("soldeFidelite", clientService.retrieveFidelite(username));
    }

    @GetMapping("/{username}/estVfp")
    public Map estVfp(@PathVariable("username") String username) throws Exception{
        if(StringUtils.isEmpty(username))
            throw new IllegalArgumentException();

        return Collections.singletonMap("nbJoursRestant", 0);
    }



    @Autowired
    public void setUserService(ClientService clientService) {
        this.clientService = clientService;
    }
}
