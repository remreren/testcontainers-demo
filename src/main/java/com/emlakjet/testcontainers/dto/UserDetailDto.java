package com.emlakjet.testcontainers.dto;

import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public final class UserDetailDto {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private Timestamp createdAt;
}
