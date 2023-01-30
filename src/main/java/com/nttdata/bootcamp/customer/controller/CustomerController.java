package com.nttdata.bootcamp.customer.controller;

import com.nttdata.bootcamp.customer.entity.Customer;
import com.nttdata.bootcamp.customer.entity.dto.BusinessCustomerDto;
import com.nttdata.bootcamp.customer.entity.dto.PersonalCustomerDto;
import com.nttdata.bootcamp.customer.service.CustomerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //Customer search
    @GetMapping("/findAll")
    public Flux<Customer> findAllCustomers() {
        return customerService.findAllCustomers();
    }

    //Search for customers by DNI
    @CircuitBreaker(name = "customer", fallbackMethod = "fallBackCustomer")
    @GetMapping("/findByDni/{dni}")
    public Mono<Customer> findCustomerByDni(@PathVariable("dni") String dni) {
        return customerService.findCustomerByDni(dni);
    }

    //Save personal customer
    @CircuitBreaker(name = "customer", fallbackMethod = "fallBackCustomer")
    @PostMapping(value = "/savePersonalCustomer")
    public Mono<Customer> savePersonalCustomer(@Valid @RequestBody PersonalCustomerDto customer) {
        return customerService.savePersonalCustomer(customer);
    }

    //Save business customer
    @CircuitBreaker(name = "customer", fallbackMethod = "fallBackCustomer")
    @PostMapping(value = "/saveBusinessCustomer")
    public Mono<Customer> saveBusinessCustomer(@Valid @RequestBody BusinessCustomerDto customer) {
        return customerService.saveBusinessCustomer(customer);
    }

    //Update customer status
    @PutMapping("/updateStatus/{dni}")
    public Mono<Customer> updateCustomerStatus(@PathVariable("dni") String dni, @RequestBody Customer customer) {
        return customerService.updateCustomerStatus(customer);
    }

    //Update customer address
    @PutMapping("/updateAddress/{dni}")
    public Mono<Customer> updateCustomerAddress(@PathVariable("dni") String dni, @RequestBody Customer customer) {
        return customerService.updateCustomerAddress(customer);
    }

    //Delete customer
    @DeleteMapping("/delete/{dni}")
    public Mono<Customer> deleteCustomer(@PathVariable("dni") String dni) {
        return customerService.deleteCustomer(dni);
    }

    public Mono<String> fallBackCustomer(RuntimeException runtimeException){
        return Mono.just("Customer Microservice is not responding");
    }

}
