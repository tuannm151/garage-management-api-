package com.example.GARA_API.services.appUser;

import com.example.GARA_API.exception.NotFoundException;
import com.example.GARA_API.model.*;
import com.example.GARA_API.repository.CustomerRepo;
import com.example.GARA_API.repository.RoleRepo;
import com.example.GARA_API.repository.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AppUserServicesImpl implements AppUserServices {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final CustomerRepo customerRepo;
    private final Argon2PasswordEncoder argon2PasswordEncoder;

    @Override
    public User saveAppUser(User user) {
        user.setPassword(argon2PasswordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(roleRepo.findRoleByName("ROLE_USER")));
        return userRepo.save(user);
    }
    @Override
    public void deleteAppUserById(Long appUserId) {
        if(!userRepo.existsById(appUserId)) throw new NotFoundException("User not found");
        userRepo.deleteById(appUserId);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToAppUser(String userName, String roleName) {
        log.info("Adding a role {} to an exist user {}", roleName, userName);
        User user = userRepo.findByUserNameIgnoreCase(userName);
        Role role = roleRepo.findRoleByName(roleName);
        if(role == null) {
            throw new NotFoundException("Role not found");
        }
        if(user == null) {
            throw new NotFoundException("User not found");
        }
        user.getRoles().add(role);
    }

    @Override
    public User findUserByUserName(String userName) {
        User user = userRepo.findByUserNameIgnoreCase(userName);
        if(user == null) {
            throw new NotFoundException("User not found");
        }
        return user;
    }

    @Override
    public void addCustomer(Customer customer, User user) {
        customer.setUser(user);
        customerRepo.save(customer);
    }

    @Override
    public List<User> getAppUsers() {
        return userRepo.findAll();
    }

}
