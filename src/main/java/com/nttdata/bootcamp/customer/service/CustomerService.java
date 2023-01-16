package com.nttdata.bootcamp.customer.service;

import com.nttdata.bootcamp.customer.entity.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//Interface Service
public interface CustomerService {

    Flux<Customer> findAll();
    Mono<Customer> findByDni(String dni);
    Mono<Customer> save(Customer customer);
    Mono<Customer> updateAddress(Customer dataCustomer);
    Mono<Customer> updateStatus(Customer dataCustomer);
    Mono<Void> delete(String dni);
    Customer saveInitServices(Customer dataCustomer);

}
