package com.example.security_demo.domain.command;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RegisterCmd {
    private String email;
    private String userName;
    private String passWord;
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;
    List<Long> rolesId;

}
