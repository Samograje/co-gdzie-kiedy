package org.polsl.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

  public NotFoundException(String resourceName, String fieldName, Object fieldValue) {
    super(String.format("Nie istnieje %s o %s: '%s'", resourceName, fieldName, fieldValue));
  }

}
