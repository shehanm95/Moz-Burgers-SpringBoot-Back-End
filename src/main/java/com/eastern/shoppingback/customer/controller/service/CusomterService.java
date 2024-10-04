package com.eastern.shoppingback.customer.controller.service;

import com.eastern.shoppingback.customer.controller.model.Customer;

import java.util.List;

public interface CusomterService {
    public List<Customer> getAll();

    Customer save(Customer customer);

    void delete(int id);
}
