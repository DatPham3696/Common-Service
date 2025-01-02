package com.storage_service.storage.controller;

import com.evo.common.client.iam.IamClient;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file/public")
@RequiredArgsConstructor
public class testController {

  private final IamClient iamClient;

  @GetMapping("/test/{email}")
  ResponseEntity<?> getAu(@PathVariable("email") String email) {
    return ResponseEntity.ok(iamClient.getUserAuthority(email));
  }

}
