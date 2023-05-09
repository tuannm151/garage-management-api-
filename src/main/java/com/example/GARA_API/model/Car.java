package com.example.GARA_API.model;

import com.example.GARA_API.enums.CarType;
import com.example.GARA_API.enums.Manufacture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String model;

    @NotNull
    @Min(1900)
    private Integer year;

    @NotNull
    private Manufacture manufacture;

    @NotNull
    private CarType type;

    private String licensePlate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Car(String model, Integer year, Manufacture manufacture, CarType type, String licensePlate) {
        this.model = model;
        this.year = year;
        this.manufacture = manufacture;
        this.type = type;
        this.licensePlate = licensePlate;
    }
}
