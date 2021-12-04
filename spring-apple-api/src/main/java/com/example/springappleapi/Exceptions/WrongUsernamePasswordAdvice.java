package com.example.springappleapi.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class WrongUsernamePasswordAdvice {

  @ResponseBody
  @ExceptionHandler(WrongUsernamePasswordException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String handle(WrongUsernamePasswordException ex) {
    return ex.getMessage();
  }
}