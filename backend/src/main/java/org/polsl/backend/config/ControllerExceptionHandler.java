package org.polsl.backend.config;

import org.polsl.backend.dto.ApiBasicResponse;
import org.polsl.backend.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  ErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    ErrorResult errorResult = new ErrorResult();
    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      errorResult.getFieldErrors()
        .add(new FieldValidationError(fieldError.getField(),
          fieldError.getDefaultMessage()));
    }
    return errorResult;
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  @ResponseBody
  ApiBasicResponse handleNotFoundException(NotFoundException e) {
    return new ApiBasicResponse(false, e.getMessage());
  }

}
