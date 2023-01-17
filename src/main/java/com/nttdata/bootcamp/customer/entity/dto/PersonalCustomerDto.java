package com.nttdata.bootcamp.customer.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalCustomerDto {

    @NotNull(message = "DNI is mandatory")
    @Size(min = 8, max = 8, message = "DNI must be 8 characters long")
    private String dni;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Surname is mandatory")
    private String surName;

    @NotBlank(message = "Address is mandatory")
    private String address;
}
