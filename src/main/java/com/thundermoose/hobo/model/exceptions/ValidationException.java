package com.thundermoose.hobo.model.exceptions;

import com.thundermoose.hobo.model.ValidationError;

import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Thundermoose on 6/2/2014.
 */
public class ValidationException extends BaseException {
  private Set<ValidationError> errors = new HashSet<>();

  public <E> ValidationException(Set<ConstraintViolation<E>> constraintViolations) {
    errors = new HashSet<>();
    for (ConstraintViolation e : constraintViolations) {
      errors.add(new ValidationError(e.getPropertyPath().toString(), e.getMessage()));
    }
  }

  public Set<ValidationError> getErrors() {
    return errors;
  }

  public void setErrors(Set<ValidationError> errors) {
    this.errors = errors;
  }
}
