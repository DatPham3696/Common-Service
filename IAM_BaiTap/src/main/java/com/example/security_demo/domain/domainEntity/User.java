package com.example.security_demo.domain.domainEntity;

import com.example.security_demo.domain.command.RegisterCmd;
import com.example.security_demo.domain.command.UpdateInforCmd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class User extends Auditable {
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
    private Boolean isRoot;
    private List<RoleUser> roleUser = new ArrayList<>();

    public User(RegisterCmd cmd, // veesiet gon lai la registercmd
                PasswordEncoder passwordEncoder,
                String verificationCode,
                Supplier<String> userKclId,
                List<Long> rolesExist) {

        this.id = UUID.randomUUID().toString();
        this.userName = cmd.getUserName();
        this.passWord = passwordEncoder.encode(cmd.getPassWord());
        this.email = cmd.getEmail();
        this.phoneNumber = cmd.getPhoneNumber();
        this.address = cmd.getAddress();
        this.dateOfBirth = cmd.getDateOfBirth();
        this.verificationCode = verificationCode;
        this.keyclUserId = userKclId.get();
        this.deleted = false;
        this.isRoot = false;
        this.enabled = false;
        this.assignUserRole(rolesExist);
    }

    public void assignUserRole(List<Long> roleExits) {
        if (roleExits != null && !roleExits.isEmpty()) {
            this.roleUser = new ArrayList<>();
            roleExits.forEach(roleId -> {
                RoleUser roleUser = new RoleUser();
//                roleUser.setId(15L);
                roleUser.setUserId(this.id);
                roleUser.setRoleId(roleId);
                log.info(this.roleUser.toString());
                this.roleUser.add(roleUser);
            });
        }
    }

    public void update(UpdateInforCmd updateInforCommand, PasswordEncoder passwordEncoder) {
        this.userName = updateInforCommand.getUserName();
        this.phoneNumber = updateInforCommand.getPhoneNumber();
        this.passWord = passwordEncoder.encode(updateInforCommand.getPassWord());
        this.address = updateInforCommand.getAddress();
        this.dateOfBirth = updateInforCommand.getDateOfBirth();
    }

    public void softDelete() {
        this.deleted = true;
    }


}
