package com.example.GARA_API.controller;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.GARA_API.exception.CustomAuthenticationException;
import com.example.GARA_API.model.Customer;
import com.example.GARA_API.model.User;
import com.example.GARA_API.model.Role;
import com.example.GARA_API.services.appUser.AppUserServices;
import com.example.GARA_API.services.AuthServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.GARA_API.filter.CustomAuthenticationFilter.generateToken;
import static com.example.GARA_API.configuration.SecurityConstants.SECRET;
import static com.example.GARA_API.configuration.SecurityConstants.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final AppUserServices appUserServices;
    private final AuthServices authServices;
    @PostMapping("/users/save")
    public ResponseEntity<User> saveAppUser(@RequestBody @Valid User user) {
        URI uri =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/save").toUriString());

        return ResponseEntity.created(uri).body(appUserServices.saveAppUser(user));
    }

    @PostMapping("/users/customer")
    public ResponseEntity<?> addCustomer(@RequestBody @Valid Customer customer, Authentication authentication) {
        User user = appUserServices.findUserByUserName(authentication.getName());
        appUserServices.addCustomer(customer, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/users/refreshtoken")
    public void renewAccessToken(HttpServletRequest request, HttpServletResponse response) throws CustomAuthenticationException {
        try {
            User user = authServices.loadUserFromHttpRequest(request);
            String refresh_token = request.getHeader(AUTHORIZATION).replace(TOKEN_PREFIX, "");
            List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
            String access_token = generateToken(roles, request.getRequestURL().toString(),
                    user.getUserName(), Algorithm.HMAC256(SECRET.getBytes()));
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", access_token);
            tokens.put("refresh_token", refresh_token);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        } catch (Exception e) {
            throw new CustomAuthenticationException(e.getMessage());
        }
    }

    @PostMapping("/admin/roles/save")
    public ResponseEntity<Role> saveAppUser(@RequestBody @Valid Role role) {
        URI uri =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/admin/roles/save").toUriString());
        return ResponseEntity.created(uri).body(appUserServices.saveRole(role));
    }

    @PostMapping("/admin/roles/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody @Valid RoleToUserForm form) {
        appUserServices.addRoleToAppUser(form.getUserName(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/admin/users/delete/{id}")
    public ResponseEntity<User> deleteAppUserById(@PathVariable Long id) {
        appUserServices.deleteAppUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/admin/users/list")
    public ResponseEntity<List<User>> getAppUsers() {
        return ResponseEntity.ok().body(appUserServices.getAppUsers());
    }
}

@Data
class RoleToUserForm {
    @NotBlank(message = "username is missing")
    private String userName;
    @NotBlank(message = "password is missing")
    private String roleName;
}