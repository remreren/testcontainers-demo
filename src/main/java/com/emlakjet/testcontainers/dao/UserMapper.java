package com.emlakjet.testcontainers.dao;

import com.emlakjet.testcontainers.dto.UserDetailDto;
import com.emlakjet.testcontainers.dto.UserDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(UserEntity entity);

    UserDetailDto toUserDetailDto(UserEntity entity);

    UserEntity toUserEntity(UserDto dto);
}
