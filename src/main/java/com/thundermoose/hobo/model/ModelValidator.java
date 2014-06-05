package com.thundermoose.hobo.model;

import com.thundermoose.hobo.model.exceptions.ValidationException;
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

  public void validate(Object obj) {
    Set<ConstraintViolation<Object>> errors = validator.validate(obj);
    if (errors.size() > 0) {
      throw new ValidationException(errors);
    }
  }

}
