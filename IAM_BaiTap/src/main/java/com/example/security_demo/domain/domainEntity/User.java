package com.example.security_demo.domain.domainEntity;

import com.example.security_demo.application.dto.request.user.RoleUserRequest;
import com.example.security_demo.domain.command.RegisterCmd;
import com.example.security_demo.domain.command.RoleUserCmd;
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
      Supplier<String> userKclId
  ) {

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
    this.assignUserRole(List.of(), cmd.getRolesId());
  }

  public void assignUserRole(List<RoleUser> existingUserRoles, List<Long> newRoleIds) {
    // Lấy danh sách roleId hiện có từ danh sách RoleUser
    List<Long> existingRoleIds = existingUserRoles.stream()
        .map(RoleUser::getRoleId)
        .toList();

    // Tìm các role mới cần thêm (không tồn tại trong existingRoleIds)
    List<RoleUser> rolesToAdd = newRoleIds.stream()
        .filter(roleId -> !existingRoleIds.contains(roleId)) // Lọc role chưa tồn tại
        .map(roleId -> {
          RoleUser roleUser = new RoleUser();
          roleUser.setUserId(this.id);
          roleUser.setRoleId(roleId);
          roleUser.setDeleted(false); // Đảm bảo role mới không bị đánh dấu là xóa
          return roleUser;
        })
        .toList();

    // Xác định các role cần cập nhật hoặc đánh dấu là xóa
    List<RoleUser> rolesToUpdateOrDelete = existingUserRoles.stream()
        .map(existingRole -> {
          if (newRoleIds.contains(
              existingRole.getRoleId())) { // neu rolecu ton tai trong list role moi -> set role cu la false
            existingRole.setDeleted(false);
          } else { // neu role cu khong ton tai trong list role moi -> set role cu la true
            existingRole.setDeleted(true);
          }
          return existingRole;
        })
        .toList();

    List<RoleUser> result = new ArrayList<>();
    result.addAll(rolesToAdd);
    result.addAll(rolesToUpdateOrDelete);

    // Cập nhật lại danh sách roles của user
    this.roleUser = result;
  }


  public void update(UpdateInforCmd updateInforCommand, PasswordEncoder passwordEncoder) {
    if (updateInforCommand == null) {
      return;
    }
    if (updateInforCommand.getUserName() != null) {
      this.userName = updateInforCommand.getUserName();
    }
    if (updateInforCommand.getPhoneNumber() != null) {
      this.phoneNumber = updateInforCommand.getPhoneNumber();
    }
    if (updateInforCommand.getPassWord() != null) {
      this.passWord = passwordEncoder.encode(updateInforCommand.getPassWord());
    }
    if (updateInforCommand.getPassWord() != null) {
      this.address = updateInforCommand.getAddress();
    }
    if (updateInforCommand.getDateOfBirth() != null) {
      this.dateOfBirth = updateInforCommand.getDateOfBirth();
    }
  }

  public void softDelete() {
    this.deleted = true;
  }

  public void updateRoleUser(List<RoleUser> roleUserExisted, RoleUserCmd cmd) {
    this.assignUserRole(roleUserExisted, cmd.getRoleIds());
  }

}
