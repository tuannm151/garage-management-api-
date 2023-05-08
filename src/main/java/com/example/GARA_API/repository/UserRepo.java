package com.example.GARA_API.repository;

import com.example.GARA_API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUserNameIgnoreCase(String userName);
    User getByUserNameIgnoreCase(String userName);
    boolean existsByUserNameIgnoreCase(String userName);
    boolean existsByEmailIgnoreCase(String email);
}
