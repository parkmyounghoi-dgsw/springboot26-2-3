package com.example.ex06.controller;

import com.example.ex06.entity.Member;
import com.example.ex06.entity.Product;
import com.example.ex06.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id){
        System.out.println("id = "+id);
        return productService.findById(id);
    }
}
