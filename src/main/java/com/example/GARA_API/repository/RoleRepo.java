package com.example.GARA_API.repository;

import com.example.GARA_API.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
}