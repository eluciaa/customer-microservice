package com.nttdata.bootcamp.customer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "customer")
public class Customer {

    @Id
    private String id;

    @NotNull(message = "DNI is mandatory")
    @Size(min = 8, max = 8, message = "DNI must be 8 characters long")
    private String dni;

    @NotBlank(message = "Type customer is mandatory")
    private String typeCustomer;

    @NotNull(message = "Flag VIP is mandatory")
    private Boolean flagVip;

    @NotNull(message = "Flag Pyme is mandatory")
    private Boolean flagPyme;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Surname is mandatory")
    private String surName;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Status is mandatory")
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreatedDate
    private Date creationDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @LastModifiedDate
    private Date modificationDate;

}
