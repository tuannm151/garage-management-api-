package com.example.GARA_API.model;

import com.example.GARA_API.validation.UniqueEmail;
import com.example.GARA_API.validation.UniqueUsername;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Firstname is missing")
    @Size(max = 40, message = "Password must be smaller than 40 characters long")
    @Column(nullable = false, length = 40)
    private String firstName;

    @NotBlank(message = "Lastname is missing")
    @Size(max = 40, message = "Password must be smaller than 40 characters long")
    @Column(nullable = false, length = 40)
    private String lastName;

    @NotBlank(message = "Username is missing")
    @Size(min = 6, max = 20, message = "Username must be between six and 20 characters long")
    @Column(unique = true, nullable = false, length = 20)
    @UniqueUsername
    private String userName;

    @NotBlank(message = "Email is missing")
    @Size(max = 128, message = "Password must be smaller than 128 characters long")
    @Email(message = "Wrong email format")
    @Column(unique = true, nullable = false, length = 128)
    @UniqueEmail
    private String email;

    @NotBlank(message = "Password is missing")
    @Size(min = 6, message = "Password must be equal or greater than 6 characters long")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public User(String firstName, String lastName, String userName, String email, String password, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
