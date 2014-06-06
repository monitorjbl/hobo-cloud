package com.thundermoose.hobo.model.exceptions;

/**
 * Created by Thundermoose on 6/5/2014.
 */
public class DockerApiException extends BaseException {
  public DockerApiException() {
    super();
  }

  public DockerApiException(String msg) {
    super(msg);
  }

  public DockerApiException(Throwable t) {
    super(t);
  }

  public DockerApiException(String msg, Throwable t) {
    super(msg, t);
  }
}
