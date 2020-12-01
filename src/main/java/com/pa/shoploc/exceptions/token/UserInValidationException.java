package com.pa.shoploc.exceptions.token;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
@ResponseStatus(code = HttpStatus.LOCKED, reason = "User not validate")
public class UserInValidationException extends Exception {

}
