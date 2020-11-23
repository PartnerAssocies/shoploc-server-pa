package com.pa.shoploc.exceptions.token;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Invalid refresh token")
public class InvalidRefreshTokenException extends Exception{
}
