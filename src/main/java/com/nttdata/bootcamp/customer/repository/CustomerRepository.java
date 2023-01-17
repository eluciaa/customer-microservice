package com.nttdata.bootcamp.customer.repository;

import com.nttdata.bootcamp.customer.entity.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

//Mongodb Repository
@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
}
