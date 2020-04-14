package tech.sollabs.urlshortener.exception;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler({HttpRequestMethodNotSupportedException.class,
      HttpMediaTypeNotSupportedException.class, NoHandlerFoundException.class, NotFoundException.class})
  public ResponseEntity<ExceptionResponse> handleNotFound(Exception e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ExceptionResponse.from("Not Found"));
  }

  @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
  public ResponseEntity<ExceptionResponse> handleInvalidParameterException(Exception e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ExceptionResponse.from("Invalid Parameter"));
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    BindingResult result = e.getBindingResult();

    if (result.getErrorCount() == 0) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ExceptionResponse.from(e.getMessage()));
    }

    ExceptionResponse body = result.getFieldErrors()
        .stream()
        .findFirst()
        .map(error -> ExceptionResponse.from(String.join("_", error.getField(), error.getDefaultMessage())))
        .orElseGet(() -> result.getAllErrors()
            .stream()
            .findFirst()
            .map(error -> ExceptionResponse.from(error.getDefaultMessage()))
            .get());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(body);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ExceptionResponse> handleDataException(DataIntegrityViolationException e) {
    if (e.getCause() instanceof ConstraintViolationException) {
      String message = e.getCause().getMessage();
      String[] keyValue = StringUtils.substringsBetween(message, "(", ")");
      if (StringUtils.endsWith(message, "already exists.")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ExceptionResponse.from(String.join("_", keyValue[0], "Already in Use")));
      }
      if (StringUtils.contains(message, "violates foreign key constraint ")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ExceptionResponse.from(String.join("_", keyValue[0], "Invalid")));
      }
    }

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ExceptionResponse.from(e.getMessage()));
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  public ResponseEntity<ExceptionResponse> handleHttpMediaTypeNotAcceptableException() {
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
        .contentType(MediaType.APPLICATION_JSON)
        .body(ExceptionResponse.from("Not Acceptable Media Type"));
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(HttpStatusCodeException.class)
  public ResponseEntity<String> handleHttpStatusCodeException(HttpStatusCodeException e) {
    return ResponseEntity.status(e.getStatusCode())
        .body(e.getResponseBodyAsString());
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse exception(Exception e) {
    return ExceptionResponse.from("Unknown Error");
  }

  public static class ExceptionResponse {

    private String message;

    private ExceptionResponse(String message) {
      this.message = message;
    }

    public static ExceptionResponse from(String message) {
      return new ExceptionResponse(message);
    }

    public String getMessage() {
      return message;
    }
  }
}
