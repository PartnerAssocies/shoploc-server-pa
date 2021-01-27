package com.pa.shoploc.exceptions.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED,reason = "No more money available for this user")
public class NotEnoughMoneyException extends Exception{
}
