package com.nttdata.bootcamp.customer.repository;

import com.nttdata.bootcamp.customer.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

//Mongodb Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, String> {
}
