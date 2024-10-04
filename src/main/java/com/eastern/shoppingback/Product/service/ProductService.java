package com.eastern.shoppingback.Product.service;

import com.eastern.shoppingback.Product.model.Product;

import java.util.List;
import java.util.Optional;


public interface ProductService {

    List<Product> getAll();

    Product savep(Product product);
    void savep(List<Product> product);

    Optional<Product> getById(int id);

    void delete(int id);
}
