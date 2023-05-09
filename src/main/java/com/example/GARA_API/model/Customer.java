package com.example.GARA_API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 6, message = "Phone number not valid")
    @Column(unique = true, length = 15)
    public String phone;

    @Size(min = 6, message = "Address not valid")
    @Column(length = 1000)
    public String address;

    @OneToOne(mappedBy = "customer")
    private User user;

    public Customer(String phone, String address) {
        this.phone = phone;
        this.address = address;
    }
}
