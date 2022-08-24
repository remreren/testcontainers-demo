package com.emlakjet.testcontainersdemo.repo;

import com.emlakjet.testcontainersdemo.model.AdvertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertRepository extends JpaRepository<AdvertEntity, Long> {
}
