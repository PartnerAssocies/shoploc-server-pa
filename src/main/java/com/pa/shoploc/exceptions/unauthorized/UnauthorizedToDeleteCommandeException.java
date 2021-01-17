package com.pa.shoploc.exceptions.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN,reason = "Commande is not in state (PANNIER | EN_ATTENTE_DE_PAIEMENT) ")
public class UnauthorizedToDeleteCommandeException extends Exception{
}
