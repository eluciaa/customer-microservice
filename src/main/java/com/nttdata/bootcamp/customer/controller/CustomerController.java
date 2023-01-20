package com.nttdata.bootcamp.customer.controller;

import com.nttdata.bootcamp.customer.entity.dto.BusinessCustomerDto;
import com.nttdata.bootcamp.customer.entity.dto.PersonalCustomerDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import com.nttdata.bootcamp.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.nttdata.bootcamp.customer.entity.Customer;
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
    @GetMapping("/")
    public Flux<Customer> findAll() {
        return customerService.findAll();
    }

    //Search for customers by DNI
    @GetMapping("/findByDNI/{dni}")
    public Mono<Customer> findByCustomerDNI(@PathVariable("dni") String dni) {
        return customerService.findByDni(dni);
    }

    //Save personal customer
    @PostMapping(value = "/savePersonalCustomer")
    public Mono<Customer> savePersCust(@Valid @RequestBody PersonalCustomerDto customer) {
        return customerService.savePersCust(customer);
    }

    //Save business customer
    @PostMapping(value = "/saveBusinessCustomer")
    public Mono<Customer> saveBusCust(@Valid @RequestBody BusinessCustomerDto customer) {
        return customerService.saveBusCust(customer);
    }

    //Update address customer
    @PutMapping("/updateCustomerAddress/{dni}")
    public Mono<Customer> updateCustomerAddress(@PathVariable("dni") String dni, @RequestBody Customer customer) {
        return customerService.updateAddress(customer);
    }

    //Update status customer
    @PutMapping("/updateCustomerStatus/{dni}")
    public Mono<Customer> updateCustomerStatus(@PathVariable("dni") String dni, @RequestBody Customer customer) {
        return customerService.updateStatus(customer);
    }

    //Delete customer
    @DeleteMapping("/delete/{dni}")
    public Mono<Customer> deleteCustomer(@PathVariable("dni") String dni) {
        return customerService.delete(dni);
    }

}
