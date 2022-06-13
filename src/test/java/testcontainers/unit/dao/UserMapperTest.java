package testcontainers.unit.dao;

import com.emlakjet.testcontainers.dao.*;
import com.emlakjet.testcontainers.dto.UserDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void should_convert_user_entity_to_user_dto() {

        //Given
        var entity = new UserEntity(1L, "Emre", "Eren", "remreren", LocalDateTime.now(), LocalDateTime.now());

        //When
        var result = UserMapper.INSTANCE.toUserDto(entity);

        //Then
        assertEquals(1L, result.getId());
        assertEquals("Emre", result.getName());
        assertEquals("Eren", result.getSurname());
        assertEquals("remreren", result.getUsername());
    }

    @Test
    void should_convert_user_entity_to_user_detail_dto() {

        //Given
        var createdTime = LocalDateTime.now();
        var entity = new UserEntity(1L, "Emre", "Eren", "remreren", createdTime, LocalDateTime.now());

        //When
        var result = UserMapper.INSTANCE.toUserDetailDto(entity);

        //Then
        assertEquals(1L, result.getId());
        assertEquals("Emre", result.getName());
        assertEquals("Eren", result.getSurname());
        assertEquals("remreren", result.getUsername());
        assertEquals(createdTime, result.getCreatedAt());
    }

    @Test
    void should_convert_user_dto_to_user_entity() {

        //Given
        var userDto = new UserDto(1L, "Emre", "Eren", "remreren");

        //When
        var result = UserMapper.INSTANCE.toUserEntity(userDto);

        //Then
        assertEquals(1L, result.getId());
        assertEquals("Emre", result.getName());
        assertEquals("Eren", result.getSurname());
        assertEquals("remreren", result.getUsername());
    }
}