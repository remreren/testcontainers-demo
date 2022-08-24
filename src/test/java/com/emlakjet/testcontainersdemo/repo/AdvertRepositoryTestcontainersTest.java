package com.emlakjet.testcontainersdemo.repo;

import com.emlakjet.testcontainersdemo.model.AdvertEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest
class AdvertRepositoryTestcontainersTest {

    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private AdvertRepository advertRepository;

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
    }

    @Test
    void should_insert_advert() {

        // Given
        var advert = new AdvertEntity(1L, "Maltepede kiralık ev", "Maltepede kiralık 1+1 ev", BigDecimal.valueOf(10000L));

        // When
        var result = advertRepository.save(advert);

        // Then
        assertEquals(advert.toString(), result.toString());

    }
}