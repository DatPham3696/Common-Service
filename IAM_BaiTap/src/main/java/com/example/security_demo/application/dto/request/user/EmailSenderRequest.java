package com.example.security_demo.application.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailSenderRequest {

  String to;
  String subject;
  String text;
}
