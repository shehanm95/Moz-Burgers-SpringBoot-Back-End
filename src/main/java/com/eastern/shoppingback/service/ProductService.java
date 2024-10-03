package com.eastern.shoppingback.service;

import com.eastern.shoppingback.model.Product;

import java.util.List;
import java.util.Optional;


public interface ProductService {

    List<Product> getAll();

    Product savep(Product product);
    void savep(List<Product> product);

    Optional<Product> getById(int id);
}
