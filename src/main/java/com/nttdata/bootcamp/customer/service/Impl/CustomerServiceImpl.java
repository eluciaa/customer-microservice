package com.nttdata.bootcamp.customer.service.Impl;

import com.nttdata.bootcamp.customer.entity.Customer;
import com.nttdata.bootcamp.customer.repository.CustomerRepository;
import com.nttdata.bootcamp.customer.service.CustomerService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;


@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Flux<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Mono<Customer> findByDni(String dni) {
        return customerRepository.findAll()
                .filter(x -> x.getDni().equals(dni))
                .doOnComplete(() -> log.info("No customer found with DNI: " + dni))
                .next();
    }

    @Override
    public Mono<Customer> save(Customer dataCustomer) {
        return findByDni(dataCustomer.getDni())
                .hasElement()
                .flatMap(exist -> exist ? Mono.empty() : customerRepository.save(dataCustomer)
                        .doOnError(error -> log.error("Error saving customer with DNI " + dataCustomer.getDni() + ": " + error.getMessage())));
    }

    /*@Override
    public Mono<Customer> updateAddress(Customer dataCustomer) {
        Mono<Customer> customerMono = findByDni(dataCustomer.getDni());
        try {
            Customer customer = customerMono.block();
            assert customer != null;
            customer.setAddress(dataCustomer.getAddress());
            customer.setModificationDate(dataCustomer.getModificationDate());
            return customerRepository.save(customer);
        }catch (Exception e){
            return Mono.<Customer>error(new Error("The customer with DNI " + dataCustomer.getDni() + " do not exists"));
        }
    }*/

    @Override
    public Mono<Customer> updateStatus(Customer dataCustomer) {
        Mono<Customer> customerMono = findByDni(dataCustomer.getDni());
        try {
            Customer customer = customerMono.block();
            assert customer != null;
            customer.setStatus(dataCustomer.getStatus());
            customer.setModificationDate(dataCustomer.getModificationDate());
            return customerRepository.save(customer);
        } catch (Exception e) {
            return Mono.<Customer>error(new Error("The customer with DNI " + dataCustomer.getDni() + " do not exists"));
        }
    }

    @Override
    public Mono<Void> delete(String dni) {
        Mono<Customer> customerMono = findByDni(dni);
        //customerMono.subscribe();
        try {
            return customerRepository.delete(customerMono.block());
        } catch (Exception e) {
            return Mono.<Void>error(new Error("The customer with DNI" + dni + " do not exists"));
        }
    }

}
