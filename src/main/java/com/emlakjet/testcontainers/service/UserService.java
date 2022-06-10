package com.emlakjet.testcontainers.service;

import com.emlakjet.testcontainers.repo.UserRepository;
import com.emlakjet.testcontainers.dao.UserMapper;
import com.emlakjet.testcontainers.dto.UserDetailDto;
import com.emlakjet.testcontainers.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserDto createUser(UserDto user) {
        var createdUser = userRepository.save(userMapper.toUserEntity(user));
        return userMapper.toUserDto(createdUser);
    }

    public UserDto getUserById(Long userId) {
        var user = userRepository.getUserEntityById(userId);
        if (user.isEmpty()) throw new IllegalArgumentException("User cannot be found.");
        return userMapper.toUserDto(user.get());
    }

    public UserDetailDto getUserDetailById(Long userId) {
        var user = userRepository.getUserEntityById(userId);
        if (user.isEmpty()) throw new IllegalArgumentException("User cannot be found.");
        return userMapper.toUserDetailDto(user.get());
    }
}
