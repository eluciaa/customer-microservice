package com.nttdata.bootcamp.customer.service;

import com.nttdata.bootcamp.customer.entity.Customer;
import com.nttdata.bootcamp.customer.entity.dto.BusinessCustomerDto;
import com.nttdata.bootcamp.customer.entity.dto.PersonalCustomerDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//Interface Service
public interface CustomerService {

    Flux<Customer> findAllCustomers();

    Mono<Customer> findCustomerById(String id);

    Mono<Customer> findCustomerByDni(String dni);

    Mono<Customer> savePersonalCustomer(PersonalCustomerDto dataCustomer);

    Mono<Customer> saveBusinessCustomer(BusinessCustomerDto dataCustomer);

    Mono<Customer> updateCustomerStatus(Customer dataCustomer);

    Mono<Customer> updateCustomerAddress(Customer dataCustomer);

    Mono<Customer> deleteCustomer(String dni);

}
