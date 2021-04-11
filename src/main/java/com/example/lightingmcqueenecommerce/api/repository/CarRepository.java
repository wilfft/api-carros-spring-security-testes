package com.example.lightingmcqueenecommerce.api.repository;

import com.example.lightingmcqueenecommerce.api.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> getCarById(Long id);

    List<Car> findByType(String type);
}
