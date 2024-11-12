package com.ltrha.ticket.domain.mapper;

import com.ltrha.ticket.config.security.UserPrincipal;
import com.ltrha.ticket.domain.response.user.UserResponse;
import com.ltrha.ticket.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "dateOfBirth", source = "dob")
    UserResponse toUserResponse(UserEntity userEntity);

    UserResponse fromPrincipalstoUserResponse(UserPrincipal userEntity);
}
