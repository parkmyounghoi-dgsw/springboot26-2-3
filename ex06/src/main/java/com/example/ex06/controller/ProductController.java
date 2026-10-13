package com.example.ex06.controller;

import com.example.ex06.entity.Product;
import com.example.ex06.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Product API", description = "상품 관련 API")
public class ProductController {



    private final ProductService productService;

    @GetMapping
    @Operation(summary = "상품 전체 조회", description = "등록된 모든 상품 목록을 반환합니다.")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "상품 단건 조회", description = "ID로 특정 상품을 조회합니다.")
    public Product findById(@PathVariable Long id){
        System.out.println("id = "+id);
        return productService.findById(id);
    }

    @PostMapping
    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.")
    public Product save(@RequestBody Product product){
        System.out.println(product);
        return productService.save(product);
    }
}
