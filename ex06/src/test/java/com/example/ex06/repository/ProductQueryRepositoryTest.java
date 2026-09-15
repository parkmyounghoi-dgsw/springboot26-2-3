package com.example.ex06.repository;

import com.example.ex06.entity.Product;
import com.example.ex06.entity.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * QueryDSL을 이용한 Product 커스텀 RepositoryTest
 */
@SpringBootTest
public class ProductQueryRepositoryTest {

    @Autowired
    JPAQueryFactory queryFactory;

    /**
     * 이름으로 Product 검색 (부분 일치)
     */
    @Test
    public void findByNameContaining() {
        String name = "노트";
        QProduct product = QProduct.product;
        List<Product> result = queryFactory
                .selectFrom(product)
                .where(product.name.contains(name))
                .orderBy(product.id.asc())
                .fetch();

        System.out.println("=== 이름 검색 결과 (키워드: " + name + ") ===");
        result.forEach(System.out::println);
        assertThat(result).isNotNull();
    }

    /**
     * 가격 범위로 Product 검색
     */
    @Test
    public void findByPriceBetween() {
        int minPrice = 10000;
        int maxPrice = 50000;
        QProduct product = QProduct.product;
        List<Product> result = queryFactory
                .selectFrom(product)
                .where(product.price.between(minPrice, maxPrice))
                .orderBy(product.price.asc())
                .fetch();

        System.out.println("=== 가격 범위 검색 결과 (" + minPrice + " ~ " + maxPrice + ") ===");
        result.forEach(System.out::println);
        assertThat(result).isNotNull();
    }

    /**
     * 최대 가격 이하 Product 검색
     */
    @Test
    public void findByMaxPrice() {
        int maxPrice = 30000;
        QProduct product = QProduct.product;
        List<Product> result = queryFactory
                .selectFrom(product)
                .where(product.price.loe(maxPrice))
                .orderBy(product.price.asc())
                .fetch();

        System.out.println("=== 최대 가격 이하 검색 결과 (최대: " + maxPrice + ") ===");
        result.forEach(System.out::println);
        assertThat(result).isNotNull();
    }
}
