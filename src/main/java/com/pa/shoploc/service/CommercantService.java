package com.pa.shoploc.service;

import com.pa.shoploc.bo.Commercant;
import com.pa.shoploc.dto.commercant.RegisterCommercantDTO;
import com.pa.shoploc.dto.commercant.RegisterCommercantResponseDTO;
import com.pa.shoploc.exceptions.find.UserNotFoundException;

import java.util.List;

public interface CommercantService {

    RegisterCommercantResponseDTO registerCommercant(RegisterCommercantDTO commercantDTO) throws Exception;

    List<Commercant> listAllCommercants();

    List<Commercant> listAllCommercantsInValidation();

    Commercant authorizeCommercant(String username, boolean accept) throws UserNotFoundException;
}
