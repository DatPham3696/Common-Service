package com.example.security_demo.domain.exception;


public class UserExistedException extends Exception {

  public UserExistedException(String message) {
    super(message);
  }
}
