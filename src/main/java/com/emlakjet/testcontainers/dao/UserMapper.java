package com.emlakjet.testcontainers.dao;

import com.emlakjet.testcontainers.dto.UserDetailDto;
import com.emlakjet.testcontainers.dto.UserDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    @Mapping(source = "userId", target = "id")
    UserDto toUserDto(UserEntity entity);

    @Mapping(source = "userId", target = "id")
    UserDetailDto toUserDetailDto(UserEntity entity);

    @Mapping(source = "id", target = "userId")
    UserEntity toUserEntity(UserDto dto);
}
