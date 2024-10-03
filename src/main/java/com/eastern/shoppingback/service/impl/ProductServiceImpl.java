package com.eastern.shoppingback.service.impl;

import com.eastern.shoppingback.model.Product;
import com.eastern.shoppingback.repo.ProductRepo;
import com.eastern.shoppingback.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepo productRepo;

    @Override
    public List<Product> getAll() {
        return productRepo.findAll();
    }

    @Override
    public Product savep(Product product) {
        return productRepo.save(product);
    }

    @Override
    public void savep(List<Product> products) {
       productRepo.saveAll(products);
    }

    @Override
    public Optional<Product> getById(int id) {
        return productRepo.findById(id);
    }
}
