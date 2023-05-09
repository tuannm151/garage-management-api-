package com.example.GARA_API.repository;

import com.example.GARA_API.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepo extends JpaRepository<Car, Long> {
}
