package com.nttdata.bootcamp.customer.controller;

import com.nttdata.bootcamp.customer.entity.dto.BusinessCustomerDto;
import com.nttdata.bootcamp.customer.entity.dto.PersonalCustomerDto;
import com.nttdata.bootcamp.customer.util.Constant;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import com.nttdata.bootcamp.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nttdata.bootcamp.customer.entity.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

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
    @GetMapping("/find/{dni}")
    public Mono<ResponseEntity<Customer>> findByCustomerDNI(@PathVariable("dni") String dni) {
        return customerService.findByDni(dni)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //Save personal customer
    @PostMapping(value = "/savePersonalCustomer")
    public Mono<ResponseEntity<Customer>> savePersonalCustomer(@RequestBody @Valid PersonalCustomerDto customer) {

        Customer dataCustomer = new Customer();
        Mono.just(dataCustomer).doOnNext(t -> {
                    t.setDni(customer.getDni());
                    t.setTypeCustomer(Constant.PERSONAL_CUSTOMER);
                    t.setFlagVip(false);
                    t.setFlagPyme(false);
                    t.setName(customer.getName());
                    t.setSurName(customer.getSurName());
                    t.setAddress(customer.getAddress());
                    t.setStatus(Constant.CUSTOMER_ACTIVE);
                    t.setCreationDate(new Date());
                    t.setModificationDate(new Date());
                }).onErrorReturn(dataCustomer).onErrorResume(e -> Mono.just(dataCustomer))
                .onErrorMap(f -> new InterruptedException(f.getMessage())).subscribe(x -> log.info(x.toString()));

        return customerService.save(dataCustomer)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    //Save business customer
    @PostMapping(value = "/saveBusinessCustomer")
    public Mono<Customer> saveBusinessCustomer(@RequestBody BusinessCustomerDto customer) {

        Customer dataCustomer = new Customer();
        Mono.just(dataCustomer).doOnNext(t -> {
                    t.setDni(customer.getDni());
                    t.setTypeCustomer(Constant.BUSINESS_CUSTOMER);
                    t.setFlagPyme(false);
                    t.setFlagVip(false);
                    t.setName(customer.getName());
                    t.setSurName(customer.getSurName());
                    t.setAddress(customer.getAddress());
                    t.setStatus(Constant.CUSTOMER_ACTIVE);
                    t.setCreationDate(new Date());
                    t.setModificationDate(new Date());
                }).onErrorReturn(dataCustomer).onErrorResume(e -> Mono.just(dataCustomer))
                .onErrorMap(f -> new InterruptedException(f.getMessage())).subscribe(x -> log.info(x.toString()));

        Mono<Customer> newCustomer = customerService.save(dataCustomer);
        if (newCustomer != null) {
            customerService.saveInitServices(newCustomer.block());
        }
        return newCustomer;
    }

    //Delete customer
    @DeleteMapping("/delete/{dni}")
    public ResponseEntity<Mono<Void>> deleteCustomer(@PathVariable("dni") String dni) {
        log.info("Deleting client by DNI: " + dni);
        Mono<Void> deleteCustomer = customerService.delete(dni);
        return ResponseEntity.noContent().build();
    }

}
