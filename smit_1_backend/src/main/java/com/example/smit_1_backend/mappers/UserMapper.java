package com.example.smit_1_backend.mappers;

import com.example.smit_1_backend.dtos.RegisterDto;
import com.example.smit_1_backend.dtos.UserDto;
import com.example.smit_1_backend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User registerToUser(RegisterDto registerDto);
}
