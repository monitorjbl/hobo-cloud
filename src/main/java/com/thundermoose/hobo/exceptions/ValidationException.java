package com.thundermoose.hobo.exceptions;

import com.thundermoose.hobo.model.ValidationError;

import java.util.Set;

/**
 * Created by Thundermoose on 6/2/2014.
 */
public class ValidationException extends BaseException {
  private Set<ValidationError> errors;

  public ValidationException(Set<ValidationError> errors) {
    this.errors = errors;
  }

  public Set<ValidationError> getErrors() {
    return errors;
  }

  public void setErrors(Set<ValidationError> errors) {
    this.errors = errors;
  }
}
