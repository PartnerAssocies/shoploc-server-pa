package com.pa.shoploc.service.impl;

import com.pa.shoploc.bo.Lieu;
import com.pa.shoploc.exceptions.find.LieuNotFoundException;
import com.pa.shoploc.exceptions.save.UnableToSaveLieuException;
import com.pa.shoploc.repository.LieuRepository;
import com.pa.shoploc.service.LieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LieuServiceImpl implements LieuService{
    private LieuRepository lieuRepository;

    /**
     * Cree / modifie un lieu
     * @param lieu
     * @return
     * @throws Exception
     */
    @Override
    public Lieu save(Lieu lieu) throws Exception{
        Lieu l=lieuRepository.save(lieu);
        if(l==null)
            throw new UnableToSaveLieuException();

        return l;
    }

    /**
     * Cherche un lieu grace à son ID
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Lieu findOneById(int id) throws Exception {
        Lieu l=lieuRepository.findById(id).orElse(null);
        if(l==null)
            throw new LieuNotFoundException();

        return l;

    }

    @Autowired
    public void setLieuRepository(LieuRepository lieuRepository) {
        this.lieuRepository = lieuRepository;
    }
}
