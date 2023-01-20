package com.nttdata.bootcamp.customer.service;

import com.nttdata.bootcamp.customer.entity.Customer;
import com.nttdata.bootcamp.customer.entity.dto.BusinessCustomerDto;
import com.nttdata.bootcamp.customer.entity.dto.PersonalCustomerDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//Interface Service
public interface CustomerService {

    Flux<Customer> findAll();

    Mono<Customer> findByDni(String dni);

    Mono<Customer> savePersCust(PersonalCustomerDto dataCustomer);

    Mono<Customer> saveBusCust(BusinessCustomerDto dataCustomer);

    Mono<Customer> updateStatus(Customer dataCustomer);

    Mono<Customer> delete(String dni);

}
