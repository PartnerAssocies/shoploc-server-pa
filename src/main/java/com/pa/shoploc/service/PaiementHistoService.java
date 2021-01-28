package com.pa.shoploc.service;

import com.pa.shoploc.bo.Client;
import com.pa.shoploc.bo.PaiementHisto;

import java.text.ParseException;
import java.util.List;

public interface PaiementHistoService {

    void addTransaction(double money, Client username) throws ParseException;

    List<PaiementHisto> findUserHisto(Client user);

}
