package com.pa.shoploc.service;

import com.pa.shoploc.dto.commercant.RegisterCommercantDTO;

public interface CommercantService {

    String registerCommercant(RegisterCommercantDTO commercantDTO) throws Exception;

}
