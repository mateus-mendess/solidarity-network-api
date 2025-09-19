package com.solidarity.api.mapper;

import com.solidarity.api.domain.entity.User;
import com.solidarity.api.dto.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    User toUser(UserRequest userRequest);
}
