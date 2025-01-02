package com.example.security_demo.domain.exception;

public class UserNotFoundException extends Exception {

  public UserNotFoundException(String message) {
    super(message);
  }
}
