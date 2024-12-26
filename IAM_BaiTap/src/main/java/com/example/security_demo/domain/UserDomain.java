package com.example.security_demo.domain;
import com.example.security_demo.application.dto.request.user.RegisterDTO;
import com.example.security_demo.application.dto.request.user.UpdateInforRequestDTO;
import com.example.security_demo.infrastructure.persistance.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDomain {
    private String id;
    private String userName;
    private String passWord;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;
    private boolean emailVerified = false;
    private String verificationCode;
    private String profilePicture;
    private String keyclUserId;
    private Boolean deleted;
    private boolean enabled;
    private Boolean isRoot ;
    private List<Role> roles;

    public UserDomain create(RegisterDTO registerDTO, PasswordEncoder passwordEncoder, String verificationCode){
        return UserDomain.builder()
                .userName(registerDTO.getUserName())
                .passWord(passwordEncoder.encode(registerDTO.getPassWord()))
                .email(registerDTO.getEmail())
                .phoneNumber(registerDTO.getPhoneNumber())
                .address(registerDTO.getAddress())
                .dateOfBirth(registerDTO.getDateOfBirth())
                .verificationCode(verificationCode)
                .build();
    }
    public UserDomain update(UpdateInforRequestDTO updateInforRequestDTO){
        return UserDomain.builder()
                .userName(updateInforRequestDTO.getUserName())
                .passWord(updateInforRequestDTO.getPassWord())
                .phoneNumber(updateInforRequestDTO.getPhoneNumber())
                .address(updateInforRequestDTO.getAddress())
                .dateOfBirth(updateInforRequestDTO.getDateOfBirth())
                .build();
    }
}
