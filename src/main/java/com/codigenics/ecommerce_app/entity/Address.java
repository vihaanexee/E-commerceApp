package com.codigenics.ecommerce_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 10 , message = "Please enter the street name")
    private String street;

    @NotBlank
    @Size(min = 5 , message = "Building name must be atleast 5 characters")
    private String buildingName;

    @NotBlank
    @Size(min = 3 , message = "city name must be atleast 3 characters")
    private String city;

    @NotBlank
    @Size(min = 2 , message = "state name must be atleast 2 characters")
    private String state;

    @NotBlank
    @Size(min = 3 , message = "country name must be atleast 3 characters")
    private String country;

    @NotBlank
    @Pattern(regexp = "^[0-9]{6}$" , message = "pincode must be exactly 6 characters")
    @Column(nullable = false)
    private String pincode;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();

    public Address(String buildingName, String street, String city, String state, String country, String pincode) {
        this.buildingName = buildingName;
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }
}
