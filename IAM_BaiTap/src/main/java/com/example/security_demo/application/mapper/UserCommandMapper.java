package com.example.security_demo.application.mapper;

import com.example.security_demo.application.dto.request.user.RegisterRequest;
import com.example.security_demo.application.dto.request.user.UpdateInforRequest;
import com.example.security_demo.domain.command.RegisterCmd;
import com.example.security_demo.domain.command.UpdateInforCmd;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserCommandMapper {
    RegisterRequest from(RegisterCmd cmd);
    RegisterCmd fromRegisterDto(RegisterRequest registerDTO);
    UpdateInforCmd fromUpdateInforDto(UpdateInforRequest updateInforDTO);
}
