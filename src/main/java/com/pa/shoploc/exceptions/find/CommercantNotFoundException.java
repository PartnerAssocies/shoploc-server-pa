package com.pa.shoploc.exceptions.find;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Commercant not found")
public class CommercantNotFoundException extends Exception{
}
