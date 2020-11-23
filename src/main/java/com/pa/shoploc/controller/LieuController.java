package com.pa.shoploc.controller;

import com.pa.shoploc.bo.Lieu;
import com.pa.shoploc.service.LieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lieu")
public class LieuController {
    private LieuService lieuService;

    /**
     *  Cree un lieu
     * @param lieu
     * @return
     * @throws Exception
     */
    @PostMapping("/create")
    public Lieu create(@RequestBody Lieu lieu) throws Exception{
        if(lieu==null)
            throw new IllegalArgumentException();

        return lieuService.save(lieu);
    }

    @Autowired
    public void setLieuService(LieuService lieuService) {
        this.lieuService = lieuService;
    }
}
