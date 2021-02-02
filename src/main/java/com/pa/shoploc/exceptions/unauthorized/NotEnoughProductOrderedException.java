package com.pa.shoploc.exceptions.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Not enough product ordered for the fidelite quantity")
public class NotEnoughProductOrderedException extends Exception{
}
