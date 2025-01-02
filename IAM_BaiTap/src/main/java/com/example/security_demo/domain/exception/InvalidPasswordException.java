package com.example.security_demo.domain.exception;

public class InvalidPasswordException extends Exception {

  public InvalidPasswordException(String message) {
    super(message);
  }
}
