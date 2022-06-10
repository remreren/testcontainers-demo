package testcontainers.unit.service;

import com.emlakjet.testcontainers.dao.*;
import com.emlakjet.testcontainers.dto.*;
import com.emlakjet.testcontainers.repo.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import com.emlakjet.testcontainers.service.UserService;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("should create user")
    void should_create_user() {

        //Given
        var userDto = new UserDto(0L, "Emre", "Eren", "remreren");

        when(userRepository.save(any())).thenReturn(new UserEntity(3L, "Emre", "Eren", "remreren", LocalDateTime.now(), LocalDateTime.now()));
        when(userMapper.toUserDto(any())).thenReturn(new UserDto(3L, "Emre", "Eren", "remreren"));
        when(userMapper.toUserEntity(any())).thenReturn(new UserEntity(0L, "Emre", "Eren", "remreren", null, null));

        //When
        var response = userService.createUser(userDto);

        //Then
        verify(userMapper, times(1)).toUserDto(any());
        verify(userMapper, times(1)).toUserEntity(any());
        verify(userRepository, times(1)).save(any());

        assertEquals("Emre", response.getName());
        assertEquals("Eren", response.getSurname());
        assertEquals("remreren", response.getUsername());
    }

    @Test
    @DisplayName("should get user by id")
    void should_fetch_user_by_id() {

        //Given
        when(userRepository.getUserEntityById(anyLong())).thenReturn(Optional.of(new UserEntity(3L, "Emre", "Eren", "remreren", LocalDateTime.now(), LocalDateTime.now())));
        when(userMapper.toUserDto(any())).thenReturn(new UserDto(3L, "Emre", "Eren", "remreren"));

        //When
        var response = userService.getUserById(3L);

        //Then
        verify(userRepository, times(1)).getUserEntityById(anyLong());
        verify(userMapper, times(1)).toUserDto(any());

        assertEquals(3, response.getId());
        assertEquals("Emre", response.getName());
        assertEquals("Eren", response.getSurname());
        assertEquals("remreren", response.getUsername());
    }

    @Test
    @DisplayName("should get user detail by id")
    void should_fetch_user_details_by_id() {

        //Given
        var createdAt = LocalDateTime.now();

        when(userRepository.getUserEntityById(anyLong())).thenReturn(Optional.of(new UserEntity(3L, "Emre", "Eren", "remreren", LocalDateTime.now(), LocalDateTime.now())));
        when(userMapper.toUserDetailDto(any())).thenReturn(new UserDetailDto(3L, "Emre", "Eren", "remreren", createdAt));

        //When
        var response = userService.getUserDetailById(3L);

        //Then
        verify(userRepository, times(1)).getUserEntityById(anyLong());
        verify(userMapper, times(1)).toUserDetailDto(any());

        assertEquals(3, response.getId());
        assertEquals("Emre", response.getName());
        assertEquals("Eren", response.getSurname());
        assertEquals("remreren", response.getUsername());
        assertEquals(createdAt, response.getCreatedAt());
    }
}