package com.eastern.shoppingback.Product.repo;


import com.eastern.shoppingback.Product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {

}
