package com.eastern.shoppingback.repo;


import com.eastern.shoppingback.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {

}
