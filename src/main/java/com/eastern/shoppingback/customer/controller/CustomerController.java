package com.eastern.shoppingback.customer.controller;


import com.eastern.shoppingback.customer.controller.model.Customer;
import com.eastern.shoppingback.customer.controller.service.CusomterService;
import com.eastern.shoppingback.customer.controller.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CusomterService cusomterService;

    @GetMapping("all")
    public List<Customer> getAllCustomers(){
        return cusomterService.getAll();
    }

    @PostMapping("/add")
    public Customer saveCustomer(@RequestBody Customer customer){
        return cusomterService.save(customer);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable int id){
        cusomterService.delete(id);
    }



}
