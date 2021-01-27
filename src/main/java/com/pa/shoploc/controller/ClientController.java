package com.pa.shoploc.controller;

import com.pa.shoploc.bo.Client;
import com.pa.shoploc.bo.PaiementHisto;
import com.pa.shoploc.dto.register.RegisterClientRequestDTO;
import com.pa.shoploc.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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


    @GetMapping("/{username}/paiementHisto")
    public List<PaiementHisto> paiementHisto(@PathVariable("username") String username) throws Exception{
        if(StringUtils.isEmpty(username))
            throw new IllegalArgumentException();

        return clientService.findAllPaiementHisto(username);
    }

    @Autowired
    public void setUserService(ClientService clientService) {
        this.clientService = clientService;
    }
}
