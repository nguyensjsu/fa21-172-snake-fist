package com.example.springappleapi.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class UsernameOrEmailTakenAdvice {

  @ResponseBody
  @ExceptionHandler(UsernameOrEmailTakenException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String handle(UsernameOrEmailTakenException ex) {
    return ex.getMessage();
  }
}