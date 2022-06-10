package com.emlakjet.testcontainers.controller;

import com.emlakjet.testcontainers.service.UserService;
import com.emlakjet.testcontainers.dto.UserDetailDto;
import com.emlakjet.testcontainers.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/{userId}/")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/{userId}/detail/")
    public ResponseEntity<UserDetailDto> getUserDetailById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserDetailById(userId));
    }
}
