package com.example.GARA_API.services.appUser;

import com.example.GARA_API.model.Customer;
import com.example.GARA_API.model.User;
import com.example.GARA_API.model.Role;

import java.util.List;

public interface AppUserServices {
    User saveAppUser(User user);
    Role saveRole(Role role);
    void addRoleToAppUser(String userName, String roleName);
    User findUserByUserName(String userName);
    void deleteAppUserById(Long appUserId);
    List<User> getAppUsers();
    void addCustomer(Customer customer, User user);
}
