package hs.kr.pmh.suhang.service;

import hs.kr.pmh.suhang.domain.Product;

import java.util.List;

public interface ProductService {

    Product create(Product product);

    List<Product> findAll();

    Product findById(Long id);

    Product update(Long id, Product product);

    boolean delete(Long id);
}
