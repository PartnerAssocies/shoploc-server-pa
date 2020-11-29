package com.pa.shoploc.service;

import com.pa.shoploc.dto.commercant.RegisterCommercantDTO;
import com.pa.shoploc.dto.commercant.RegisterCommercantResponseDTO;

public interface CommercantService {

    RegisterCommercantResponseDTO registerCommercant(RegisterCommercantDTO commercantDTO) throws Exception;

}
