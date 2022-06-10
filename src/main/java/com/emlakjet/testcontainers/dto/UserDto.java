package com.emlakjet.testcontainers.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public final class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String username;

}
