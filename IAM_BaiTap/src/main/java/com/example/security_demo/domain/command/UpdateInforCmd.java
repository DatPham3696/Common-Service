package com.example.security_demo.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateInforCmd {

  private String userName;
  private String phoneNumber;
  private String passWord;
  private String address;
  private LocalDate dateOfBirth;
}
