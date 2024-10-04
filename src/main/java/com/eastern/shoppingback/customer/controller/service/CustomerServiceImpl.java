package com.eastern.shoppingback.customer.controller.service;

import com.eastern.shoppingback.customer.controller.model.Customer;
import com.eastern.shoppingback.customer.controller.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    public List<Customer> getAll() {
        return customerRepo.findAll();
    }
}
