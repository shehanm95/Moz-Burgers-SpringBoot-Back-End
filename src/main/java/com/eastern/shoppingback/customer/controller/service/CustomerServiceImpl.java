package com.eastern.shoppingback.customer.controller.service;

import com.eastern.shoppingback.customer.controller.model.Customer;
import com.eastern.shoppingback.customer.controller.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CusomterService {

    @Autowired
    CustomerRepo customerRepo;

    @Override
    public List<Customer> getAll() {
        return customerRepo.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    public void delete(int id) {
        customerRepo.deleteById(id);
        System.out.println("calling delete ========");
    }
}
