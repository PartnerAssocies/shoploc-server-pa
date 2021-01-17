package com.pa.shoploc.exceptions.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN,reason = "Your not the owner of the order")
public class NotTheOwnerCommandeException extends Exception{
}
