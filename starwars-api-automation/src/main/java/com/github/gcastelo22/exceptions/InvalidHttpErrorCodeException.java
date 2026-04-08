package com.github.gcastelo22.exceptions;

/**
 * Thrown when the API returns an HTTP status code different from the expected value defined in the test.
 */
public class InvalidHttpErrorCodeException extends RuntimeException {

  public InvalidHttpErrorCodeException(String errorMsg) {
    super(errorMsg);
  }
}