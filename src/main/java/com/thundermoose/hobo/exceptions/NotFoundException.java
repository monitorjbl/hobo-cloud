package com.thundermoose.hobo.exceptions;

/**
 * Created by Thundermoose on 6/2/2014.
 */
public class NotFoundException extends RuntimeException {
  public NotFoundException() {
    super();
  }

  public NotFoundException(String msg) {
    super(msg);
  }

  public NotFoundException(Throwable t) {
    super(t);
  }

  public NotFoundException(String msg, Throwable t) {
    super(msg, t);
  }
}
