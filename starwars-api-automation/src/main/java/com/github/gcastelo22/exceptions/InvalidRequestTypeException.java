package com.github.gcastelo22.exceptions;

/**
 * Thrown when an unsupported HTTP method is passed to the framework's execution engine.
 */
public class InvalidRequestTypeException extends Exception {

  public InvalidRequestTypeException(String errorMsg) {
    super(errorMsg);
  }
}