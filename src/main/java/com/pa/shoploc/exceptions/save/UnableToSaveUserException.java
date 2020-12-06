package com.pa.shoploc.exceptions.save;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED, reason = "Unable to save User")
public class UnableToSaveUserException extends Exception{

}
