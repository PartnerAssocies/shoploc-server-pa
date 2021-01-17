package com.pa.shoploc.exceptions.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "No more stock available for this product")
public class NoMoreStockException extends Exception{
}
