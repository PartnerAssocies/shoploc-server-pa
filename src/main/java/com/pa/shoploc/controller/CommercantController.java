package com.pa.shoploc.controller;


import com.pa.shoploc.bo.Commercant;
import com.pa.shoploc.dto.commercant.RegisterCommercantDTO;
import com.pa.shoploc.dto.commercant.RegisterCommercantResponseDTO;
import com.pa.shoploc.service.CommercantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/commercant")
public class CommercantController {

    private CommercantService commercantService;

    @PostMapping("/register")
    private RegisterCommercantResponseDTO createCommercant(@RequestBody RegisterCommercantDTO commercant) throws Exception {
        return this.commercantService.registerCommercant(commercant);
    }

    @GetMapping("/listall")
    public List<Commercant> getAllCommercants(){
        return this.commercantService.listAllCommercants();
    }

    @Autowired
    public void setCommercantService(CommercantService commercantService) {
        this.commercantService = commercantService;
    }
}
