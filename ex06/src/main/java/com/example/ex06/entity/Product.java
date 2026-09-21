package com.example.ex06.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Entity // 테이블이 생성 됩니다.
@Data
public class Product {

    // 기본키 설정됨...
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(example = "사과")
    private String name;

    @Schema(example = "맛있어")
    private String description;

    @Schema(example = "2500")
    private int price;

}
