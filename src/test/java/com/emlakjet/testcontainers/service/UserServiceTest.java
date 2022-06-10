package com.emlakjet.testcontainers.service;

import com.emlakjet.testcontainers.TestcontainersDemoApplication;
import com.emlakjet.testcontainers.dao.UserEntity;
import com.emlakjet.testcontainers.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.*;
import org.testcontainers.junit.jupiter.*;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(classes = TestcontainersDemoApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class UserServiceTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private UserRepository userRepository;

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void create_user_in_database() {

        //Given
        var user = new UserEntity(0L, "Emre", "Eren", "remreren", null, null);

        //When
        var response = userRepository.save(user);

        //Then
        assertEquals(user.getName(), response.getName());
        assertEquals(user.getSurname(), response.getSurname());
        assertEquals(user.getUsername(), response.getUsername());
        assertNotNull(response.getCreatedAt());
        assertNotNull(response.getUpdatedAt());
    }

    @Test
    void fetch_user_from_database() {

        //Given
        var user = new UserEntity(0L, "Emre", "Eren", "remreren", null, null);
        var inserted = userRepository.save(user);

        //When
        var response = userRepository.getUserEntityById(inserted.getId());

        //Then
        assertTrue(response.isPresent());
        assertEquals(inserted.getId(), response.get().getId());
        assertEquals(inserted.getName(), response.get().getName());
        assertEquals(inserted.getSurname(), response.get().getSurname());
        assertEquals(inserted.getUsername(), response.get().getUsername());
        assertEquals(inserted.getCreatedAt(), response.get().getCreatedAt());
        assertEquals(inserted.getUpdatedAt(), response.get().getUpdatedAt());
    }
}