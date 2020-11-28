package com.pa.shoploc.controller;


import com.pa.shoploc.dto.commercant.RegisterCommercantDTO;
import com.pa.shoploc.service.CommercantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/commercant")
public class CommercantController {

    private CommercantService commercantService;

    @PostMapping("/register")
    private String createCommercant(@RequestBody RegisterCommercantDTO commercant) throws Exception {
        return this.commercantService.registerCommercant(commercant);
    }

    @Autowired
    public void setCommercantService(CommercantService commercantService) {
        this.commercantService = commercantService;
    }
}
