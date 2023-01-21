package com.nttdata.bootcamp.customer.service.Impl;

import com.nttdata.bootcamp.customer.entity.Customer;
import com.nttdata.bootcamp.customer.entity.dto.BusinessCustomerDto;
import com.nttdata.bootcamp.customer.entity.dto.PersonalCustomerDto;
import com.nttdata.bootcamp.customer.repository.CustomerRepository;
import com.nttdata.bootcamp.customer.service.CustomerService;
import com.nttdata.bootcamp.customer.util.Constant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Slf4j
@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Flux<Customer> findAllCustomers() {
        log.info("Searching for all customers");
        return customerRepository.findAll();
    }

    @Override
    public Mono<Customer> findCustomerByDni(String dni) {
        log.info("Searching for customer with DNI: " + dni);
        return customerRepository.findAll()
                .filter(x -> x.getDni().equals(dni))
                .next();
    }

    @Override
    public Mono<Customer> savePersonalCustomer(PersonalCustomerDto dataCustomer) {
        return findCustomerByDni(dataCustomer.getDni())
                .hasElement()
                .flatMap(dniExists -> {
                    if (dniExists) {
                        log.info("There is already a customer with that DNI");
                        return Mono.empty();
                    } else {
                        log.info("Saving for personal customer with DNI: " + dataCustomer.getDni());

                        Customer p = new Customer();
                        p.setDni(dataCustomer.getDni());
                        p.setTypeCustomer(Constant.PERSONAL_CUSTOMER);
                        p.setFlagVip(false);
                        p.setFlagPyme(false);
                        p.setName(dataCustomer.getName());
                        p.setSurName(dataCustomer.getSurName());
                        p.setAddress(dataCustomer.getAddress());
                        p.setStatus(Constant.CUSTOMER_ACTIVE);
                        p.setCreationDate(new Date());
                        p.setModificationDate(new Date());
                        return customerRepository.save(p);
                    }
                });
    }

    @Override
    public Mono<Customer> saveBusinessCustomer(BusinessCustomerDto dataCustomer) {
        return findCustomerByDni(dataCustomer.getDni())
                .hasElement()
                .flatMap(dniExists -> {
                    if (dniExists) {
                        log.info("There is already a customer with that DNI");
                        return Mono.empty();
                    } else {
                        log.info("Saving for business customer with DNI: " + dataCustomer.getDni());

                        Customer b = new Customer();
                        b.setDni(dataCustomer.getDni());
                        b.setTypeCustomer(Constant.BUSINESS_CUSTOMER);
                        b.setFlagPyme(false);
                        b.setFlagVip(false);
                        b.setName(dataCustomer.getName());
                        b.setSurName(dataCustomer.getSurName());
                        b.setAddress(dataCustomer.getAddress());
                        b.setStatus(Constant.CUSTOMER_ACTIVE);
                        b.setCreationDate(new Date());
                        b.setModificationDate(new Date());
                        return customerRepository.save(b);
                    }
                });
    }

    @Override
    public Mono<Customer> updateCustomerStatus(Customer dataCustomer) {
        return findCustomerByDni(dataCustomer.getDni())
                .flatMap(updCust -> {
                    log.info("Updating for {} customer with DNI: {}", updCust.getTypeCustomer(), updCust.getDni());

                    updCust.setStatus(dataCustomer.getStatus());
                    updCust.setModificationDate(new Date());
                    return customerRepository.save(updCust);
                });
    }

    @Override
    public Mono<Customer> updateCustomerAddress(Customer dataCustomer) {
        return findCustomerByDni(dataCustomer.getDni())
                .flatMap(updCust -> {
                    log.info("Updating address '{}' to '{}'", updCust.getAddress(), dataCustomer.getAddress());

                    updCust.setAddress(dataCustomer.getAddress());
                    updCust.setModificationDate(new Date());
                    return customerRepository.save(updCust);
                });
    }

    @Override
    public Mono<Customer> deleteCustomer(String dni) {
        log.info("Deleting client by DNI: " + dni);
        return findCustomerByDni(dni)
                .flatMap(customer -> customerRepository.delete(customer).then(Mono.just(customer)));
    }

}
