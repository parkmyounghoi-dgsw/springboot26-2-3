package hs.kr.pmh.suhang.repository;

import hs.kr.pmh.suhang.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
