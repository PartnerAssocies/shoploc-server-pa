package com.pa.shoploc.service;

import com.pa.shoploc.bo.Lieu;

public interface LieuService {
    Lieu save(Lieu lieu) throws Exception;
    Lieu findOneById(int id) throws Exception;

}
