package com.emlakjet.testcontainersdemo.repo;

import com.emlakjet.testcontainersdemo.model.AdvertEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdvertRepositoryTest {

    @Autowired
    private AdvertRepository advertRepository;

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