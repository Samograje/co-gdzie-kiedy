package org.polsl.backend.config;

import java.util.ArrayList;
import java.util.List;

public class ErrorResult {
  private List<FieldValidationError> fieldErrors = new ArrayList<>();

  public ErrorResult() {
  }

  public List<FieldValidationError> getFieldErrors() {
    return fieldErrors;
  }
}