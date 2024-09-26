package com.teamB.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

@Entity
public class TshirtOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String size;

    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Address1 is required")
    private String address1;

    private String address2;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State or Province is required")
    private String stateOrProvince;

    @NotBlank(message = "Postal Code is required")
    private String postalCode;

    @NotBlank(message = "Country is required")
    private String country;

    private String status;

    // Getters and Setters
}
