package com.thundermoose.hobo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thundermoose.hobo.model.exceptions.BaseException;
import com.thundermoose.hobo.model.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Thundermoose on 6/4/2014.
 */
@ControllerAdvice
public class ExceptionAdvice {
  private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

  @ExceptionHandler(BaseException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public BaseException handleBaseException(BaseException ex) {
    return ex;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public BaseException handleConstraintException(ConstraintViolationException ex) {
    Set<Object> o = new HashSet<>();
    Set<ConstraintViolation<Object>> c = new HashSet<>();

    // im tired and generics give me a headache. this is awful, but FUCK IT
    for (ConstraintViolation<?> e : ex.getConstraintViolations()) {
      o.add(e);
    }
    for (Object obj : o) {
      c.add((ConstraintViolation<Object>) obj);
    }

    return new ValidationException(c);
  }
}
