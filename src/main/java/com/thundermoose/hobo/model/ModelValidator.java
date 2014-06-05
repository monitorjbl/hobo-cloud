package com.thundermoose.hobo.model;

import com.thundermoose.hobo.exceptions.ValidationException;
import com.thundermoose.hobo.model.Container;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Thundermoose on 6/4/2014.
 */
@Component
public class ModelValidator {

  Validator validator;

  public ModelValidator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  public <E> void validate(E obj) {
    Set<ConstraintViolation<E>> errors = validator.validate(obj);
    if (errors.size() > 0) {
      Set<ValidationError> ve = new HashSet<>();
      for (ConstraintViolation<E> e : errors) {
        ve.add(new ValidationError(e.getPropertyPath().toString(), e.getMessage()));
      }
      throw new ValidationException(ve);
    }
  }
}
