package com.nttdata.bootcamp.customer.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessCustomerDto {
    private String dni;
    private String name;
    private String surName;
    private String address;
}
