package com.example.security_demo.application.mapper;

import com.example.security_demo.application.dto.request.user.RegisterDTO;
import com.example.security_demo.application.dto.request.user.UpdateInforRequestDTO;
import com.example.security_demo.domain.command.RegisterCommand;
import com.example.security_demo.domain.command.UpdateInforCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserCommandMapper {
    RegisterDTO from(RegisterCommand cmd);
    RegisterCommand fromRegisterDto(RegisterDTO registerDTO);
    UpdateInforCommand fromUpdateInforDto(UpdateInforRequestDTO updateInforDTO);
}
