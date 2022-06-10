package testcontainers.service;

import com.emlakjet.testcontainers.TestcontainersDemoApplication;
import com.emlakjet.testcontainers.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.*;
import org.testcontainers.junit.jupiter.*;
import org.testcontainers.containers.PostgreSQLContainer;
import com.emlakjet.testcontainers.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(classes = TestcontainersDemoApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class UserServiceTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private UserService userService;

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void should_create_user() {

        //Given
        var userDto = new UserDto(0L, "Emre", "Eren", "remreren");

        //When
        var response = userService.createUser(userDto);

        //Then
        Assertions.assertEquals("Emre", response.getName());
        Assertions.assertEquals("Eren", response.getSurname());
        Assertions.assertEquals("remreren", response.getUsername());
    }

    @Test
    void should_fetch_user_by_id() {

        //Given
        var userDto = new UserDto(0L, "Emre", "Eren", "remreren");
        var created = userService.createUser(userDto);

        //When
        var response = userService.getUserById(created.getId());

        //Then
        Assertions.assertEquals(created.getId(), response.getId());
        Assertions.assertEquals("Emre", response.getName());
        Assertions.assertEquals("Eren", response.getSurname());
        Assertions.assertEquals("remreren", response.getUsername());
    }

    @Test
    void should_fetch_user_details_by_id() {

        //Given
        var userDto = new UserDto(0L, "Emre", "Eren", "remreren");
        var created = userService.createUser(userDto);

        //When
        var response = userService.getUserDetailById(created.getId());

        //Then
        assertEquals(created.getId(), response.getId());
        assertEquals("Emre", response.getName());
        assertEquals("Eren", response.getSurname());
        assertEquals("remreren", response.getUsername());
        assertNotNull(response.getCreatedAt());
    }
}