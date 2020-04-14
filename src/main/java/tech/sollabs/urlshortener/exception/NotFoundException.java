package tech.sollabs.urlshortener.exception;

public class NotFoundException extends RuntimeException {

  public NotFoundException() {
    super("Resource Not Found");
  }
}
