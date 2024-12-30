package com.example.security_demo.domain.command;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RegisterCommand {
    private String email;
    private String userName;
    private String passWord;
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;
    List<Long> rolesId;

}
