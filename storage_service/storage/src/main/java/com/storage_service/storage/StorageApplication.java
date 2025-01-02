package com.storage_service.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StorageApplication {

  public static void main(String[] args) {
    SpringApplication.run(StorageApplication.class, args);
  }
}
