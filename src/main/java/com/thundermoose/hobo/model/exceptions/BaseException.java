package com.thundermoose.hobo.model.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Thundermoose on 6/4/2014.
 */
@JsonIgnoreProperties({"stackTrace", "suppressed", "localizedMessage"})
public abstract class BaseException extends RuntimeException {
  private String type = this.getClass().getName();

  public BaseException() {
    super();
  }

  public BaseException(String msg) {
    super(msg);
  }

  public BaseException(Throwable t) {
    super(t);
  }

  public BaseException(String msg, Throwable t) {
    super(msg, t);
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
