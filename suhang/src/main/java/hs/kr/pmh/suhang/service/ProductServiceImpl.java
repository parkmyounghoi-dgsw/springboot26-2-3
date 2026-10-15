package hs.kr.pmh.suhang.service;

import hs.kr.pmh.suhang.domain.Product;
import hs.kr.pmh.suhang.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        if (!StringUtils.hasText(product.getName())) {
            return null;
        } else if (product.getPrice() == null) {
            return null;
        }
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product update(Long id, Product product) {
        Product existing = productRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        } else if (!StringUtils.hasText(product.getName())) {
            return null;
        } else if (product.getPrice() == null) {
            return null;
        }
        existing.update(product.getName(), product.getPrice());
        return productRepository.save(existing);
    }

    @Override
    public boolean delete(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
