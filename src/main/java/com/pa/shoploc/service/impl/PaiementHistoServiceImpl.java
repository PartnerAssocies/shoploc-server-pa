package com.pa.shoploc.service.impl;

import com.pa.shoploc.bo.Client;
import com.pa.shoploc.bo.PaiementHisto;
import com.pa.shoploc.enumeration.Transaction;
import com.pa.shoploc.repository.PaiementHistoRepository;
import com.pa.shoploc.service.PaiementHistoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PaiementHistoServiceImpl implements PaiementHistoService {

    private PaiementHistoRepository paiementHistoRepository;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void addTransaction(double money, Client user) throws ParseException {
        PaiementHisto paiementHisto=new PaiementHisto();
        Date today=new Date();
        paiementHisto.setDate(sdf.parse(sdf.format(today)));
        paiementHisto.setMoney(money);
        paiementHisto.setUser(user);
        paiementHisto.setTransaction(money<0?Transaction.DEBIT:Transaction.CREDIT);

        paiementHistoRepository.save(paiementHisto);
    }

    @Override
    public List<PaiementHisto> findUserHisto(Client user) {
        return paiementHistoRepository.findByUser(user);
    }


    @Autowired
    public void setPaiementHistoRepository(PaiementHistoRepository paiementHistoRepository) {
        this.paiementHistoRepository = paiementHistoRepository;
    }
}
