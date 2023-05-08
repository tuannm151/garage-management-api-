package com.example.GARA_API.repository;

import com.example.GARA_API.model.Customer;
import com.example.GARA_API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findCustomerByIdAndUser(Long id, User user);
}