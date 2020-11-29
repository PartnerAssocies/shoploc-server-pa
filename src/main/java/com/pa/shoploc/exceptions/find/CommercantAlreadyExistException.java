package com.pa.shoploc.exceptions.find;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.IM_USED, reason = "Commercant already exists")
public class CommercantAlreadyExistException extends Exception {
}
