package com.eastern.shoppingback.Product.service.impl;

import com.eastern.shoppingback.Product.model.Product;
import com.eastern.shoppingback.Product.repo.ProductRepo;
import com.eastern.shoppingback.Product.service.ProductService;
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

    @Override
    public void delete(int id) {
        productRepo.deleteById(id);
    }
}
