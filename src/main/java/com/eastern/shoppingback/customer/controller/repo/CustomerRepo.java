package com.eastern.shoppingback.customer.controller.repo;

import com.eastern.shoppingback.customer.controller.model.Customer;
import org.hibernate.Internal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
}
