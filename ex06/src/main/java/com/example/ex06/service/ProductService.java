package com.example.ex06.service;

import com.example.ex06.entity.Member;
import com.example.ex06.entity.Product;
import com.example.ex06.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        // select * from member;
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        // select * from member where id = ?id;
        Optional<Product> optionalProduct =  productRepository.findById(id);
        return optionalProduct.get();
    }
}
